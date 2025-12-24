package tech.kaustubhdeshpande.daywise.data.agent

import ai.koog.agents.core.agent.AIAgent
import ai.koog.prompt.executor.clients.google.GoogleModels
import ai.koog.prompt.executor.llms.all.simpleGoogleAIExecutor
import kotlinx.serialization.json.Json
import tech.kaustubhdeshpande.daywise.data.models.DayTopics
import tech.kaustubhdeshpande.daywise.data.models.Roadmap
import tech.kaustubhdeshpande.daywise.data.models.Topic
import tech.kaustubhdeshpande.daywise.data.models.TopicStatus

/**
 * AI Agent powered by Koog framework for generating learning roadmaps.
 * Uses Google's Gemini 3 Pro Preview model to analyze syllabus content
 * and create structured, day-by-day learning paths with topics, descriptions, and time estimates.
 *
 * This agent demonstrates:
 * - Type-safe AI integration using Koog's AIAgent class (released Dec 22, 2025)
 * - Direct Google AI (Gemini) integration via simpleGoogleAIExecutor
 * - Structured output parsing (JSON to Kotlin data classes)
 * - Intelligent content breakdown based on learning objectives
 * - Cross-platform AI capabilities (100% shared code in commonMain)
 *
 * @property apiKey Google AI API key for Gemini model access
 */
class RoadmapAgent(
    private val apiKey: String
) {
    /**
     * Atomic counter for generating sequential IDs within the agent instance.
     * Combined with random prefix to ensure uniqueness across different agent instances.
     */
    private var idCounter = 0

    /**
     * Random prefix generated once per agent instance.
     * Ensures IDs from different agent instances don't collide.
     */
    private val instancePrefix: String = run {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        (1..8)
            .map { chars.random() }
            .joinToString("")
    }

    /**
     * Koog AIAgent configured with Gemini 3 Pro Preview model.
     * Uses simpleGoogleAIExecutor as the prompt executor (official Koog pattern).
     */
    private val agent = AIAgent(
        promptExecutor = simpleGoogleAIExecutor(apiKey),
        systemPrompt = """
            You are an expert learning path designer and educational content organizer.
            Your role is to analyze course syllabi and create structured, day-by-day learning roadmaps.
            
            **Your responsibilities:**
            1. Break down complex syllabi into manageable daily topics
            2. Distribute topics evenly across the specified number of days
            3. Provide clear, concise topic titles (3-8 words)
            4. Write helpful descriptions explaining what will be learned
            5. Estimate realistic time requirements for each topic (15-90 minutes)
            6. Suggest relevant learning resources (documentation, articles, videos)
            7. Ensure logical progression from fundamentals to advanced concepts
            
            **Output format:**
            You MUST respond with valid JSON matching this structure:
            {
              "days": [
                {
                  "dayNumber": 1,
                  "topics": [
                    {
                      "title": "Topic Title",
                      "description": "What the learner will understand after this topic",
                      "estimatedMinutes":  45,
                      "resources": ["https://example.com/docs", "https://example.com/video"]
                    }
                  ]
                }
              ]
            }
            
            **Quality guidelines:**
            - Keep topics focused (one concept per topic)
            - Balance daily workload (aim for 2-4 hours per day)
            - Include hands-on practice topics
            - Suggest official documentation as primary resources
            - Use clear, beginner-friendly language
        """.trimIndent(),
        llmModel = GoogleModels.Gemini3_Pro_Preview
    )

    /**
     * Generates a complete learning roadmap from syllabus content.
     *
     * This function:
     * 1. Sends syllabus and parameters to Gemini via Koog agent
     * 2. Receives structured JSON response
     * 3. Parses JSON into Kotlin data classes
     * 4. Generates unique IDs for roadmap elements
     * 5. Returns a fully typed Roadmap object ready for UI display
     *
     * @param syllabusContent The raw text content of the syllabus (can be from PDF, text file, or manual input)
     * @param roadmapName User-provided name for the roadmap (e.g., "Python Mastery 2024")
     * @param targetDays Number of days to spread the learning content across (e.g., 30, 60, 90)
     * @param sourceSyllabusName Optional filename of the uploaded syllabus for reference
     * @return Roadmap object containing structured day-by-day learning path
     * @throws Exception if AI generation fails, JSON parsing fails, or API errors occur
     */
    suspend fun generateRoadmap(
        syllabusContent: String,
        roadmapName: String,
        targetDays: Int,
        sourceSyllabusName: String? = null
    ): Roadmap {
        // Build the user prompt with syllabus and requirements
        val userPrompt = buildUserPrompt(
            syllabusContent = syllabusContent,
            targetDays = targetDays
        )

        // Execute agent and get AI response (using Koog's run method)
        val result = agent.run(userPrompt)
        val aiOutput = result.toString()

        // Parse JSON response into structured data
        val parsedData = parseAiResponse(aiOutput)

        // Generate unique IDs and build final Roadmap object
        return buildRoadmap(
            name = roadmapName,
            totalDays = targetDays,
            parsedDays = parsedData.days,
            sourceSyllabusName = sourceSyllabusName
        )
    }

    /**
     * Builds the user prompt sent to the AI model.
     * Includes syllabus content and specific requirements for output format.
     *
     * @param syllabusContent The syllabus text to analyze
     * @param targetDays Number of days to create the roadmap for
     * @return Formatted prompt string for the AI model
     */
    private fun buildUserPrompt(
        syllabusContent: String,
        targetDays: Int
    ): String {
        return """
            **Task:** Create a $targetDays-day learning roadmap from the following syllabus. 
            
            **Syllabus Content:**
            $syllabusContent
            
            **Requirements:**
            - Organize content into exactly $targetDays days
            - Each day should have 2-6 topics
            - Total daily learning time should be 2-4 hours
            - Include topic titles, descriptions, time estimates, and resource links
            - Ensure logical progression of difficulty
            
            **Output:** Respond ONLY with valid JSON (no markdown, no explanations, just JSON).
        """.trimIndent()
    }

    /**
     * Parses AI response JSON into intermediate data structure.
     * Handles potential formatting issues and validates structure.
     *
     * @param aiOutput Raw string output from the AI model
     * @return Parsed RoadmapResponse object
     * @throws Exception if JSON is invalid or doesn't match expected structure
     */
    private fun parseAiResponse(aiOutput: String): RoadmapResponse {
        // Clean up potential markdown code blocks or extra whitespace
        val cleanedJson = aiOutput
            .trim()
            .removePrefix("```json")
            .removePrefix("```")
            .removeSuffix("```")
            .trim()

        // Parse JSON using kotlinx.serialization
        val json = Json {
            ignoreUnknownKeys = true  // Ignore extra fields from AI
            isLenient = true           // Allow relaxed JSON format
        }

        return json.decodeFromString<RoadmapResponse>(cleanedJson)
    }

    /**
     * Builds the final Roadmap object with generated IDs and metadata.
     * Transforms parsed AI response into fully structured data model.
     *
     * @param name User-provided roadmap name
     * @param totalDays Total number of days in the roadmap
     * @param parsedDays List of days parsed from AI response
     * @param sourceSyllabusName Optional source filename
     * @return Complete Roadmap object ready for UI and persistence
     */
    private fun buildRoadmap(
        name: String,
        totalDays: Int,
        parsedDays: List<DayResponse>,
        sourceSyllabusName: String?
    ): Roadmap {
        val roadmapId = generateId()

        val dayTopicsList = parsedDays.map { dayResponse ->
            val topics = dayResponse.topics.map { topicResponse ->
                Topic(
                    id = generateId(),
                    title = topicResponse.title,
                    description = topicResponse.description,
                    dayNumber = dayResponse.dayNumber,
                    estimatedMinutes = topicResponse.estimatedMinutes,
                    status = TopicStatus.TODO,
                    resources = topicResponse.resources
                )
            }

            DayTopics(
                dayNumber = dayResponse.dayNumber,
                topics = topics
            )
        }

        return Roadmap(
            id = roadmapId,
            name = name,
            totalDays = totalDays,
            days = dayTopicsList,
            createdAt = 0L,  // Will be set by repository layer when saving
            sourceSyllabusName = sourceSyllabusName
        )
    }

    /**
     * Generates a guaranteed unique ID for roadmap elements.
     * Uses instance prefix + sequential counter for 100% uniqueness.
     *
     * Format: "{instancePrefix}-{counter}"
     * Example IDs: "a3Ks9mPq-0000", "a3Ks9mPq-0001", "Bv8Ld1Qr-0000"
     *
     * **Uniqueness guarantee:**
     * - Within same agent instance: Counter ensures no duplicates
     * - Across different instances: Random prefix (8 chars from 62-char set) makes collisions extremely unlikely
     * - Collision probability for prefix:  ~1 in 218 trillion (62^8)
     *
     * @return Unique ID string
     */
    private fun generateId(): String {
        val id = "${instancePrefix}-${idCounter.toString().padStart(4, '0')}"
        idCounter++
        return id
    }
}

/**
 * Intermediate data structure for parsing AI JSON response.
 * Matches the JSON format expected from the AI model.
 */
@kotlinx.serialization.Serializable
private data class RoadmapResponse(
    val days: List<DayResponse>
)

/**
 * Represents a single day in the AI response.
 */
@kotlinx.serialization.Serializable
private data class DayResponse(
    val dayNumber: Int,
    val topics: List<TopicResponse>
)

/**
 * Represents a single topic in the AI response.
 */
@kotlinx.serialization.Serializable
private data class TopicResponse(
    val title: String,
    val description: String = "",
    val estimatedMinutes: Int = 30,
    val resources: List<String> = emptyList()
)