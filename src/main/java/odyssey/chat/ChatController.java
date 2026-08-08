package odyssey.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;


/**
 * @author Vishnu Vardhan
 * @implNote ChatClient usage
 *
 *  No LLMs Anthropic import, no model class, no API key,no HTTP call. 
 *  Swap the starter in the pom for OpenAI or Ollama and
 * this file is untouched. That is the point of the abstraction.
 */

@RestController
@RequestMapping("/spring-ai-odyssey")
public class ChatController {
	
	private final ChatClient chatClient;
	
	
	/**
     * Spring Boot auto-configures a ChatClient.Builder for whichever
     * model starter is on the classpath. It is prototype-scoped, so
     * every injection point gets a fresh one and controllers cannot
     * interfere with each other's defaults.
     *
     * Do not reach for ChatClient.create(chatModel) instead - that
     * bypasses the auto-configured builder and silently drops
     * observability and any customizer beans.
     */
	public ChatController(ChatClient.Builder builder) {
		this.chatClient = builder.build();
	}
	
	/**
     *
     * @Valid is what actually runs the constraints on ChatRequest.
     * A blank question returns 400 before the model is ever called -
     * which also means it costs nothing.
     *
     * The chain:
     *   prompt()   begin building the request
     *   user(...)  set the user message
     *   call()     choose synchronous over streaming
     *              (this does NOT invoke the model yet)
     *   content()  THIS triggers the call and returns the text
     */
	@PostMapping("/chat")
	public ChatResponse chat(@Valid @RequestBody ChatRequest request) {
		String response = this.chatClient.prompt().user(request.question()).call().content();
		return new ChatResponse(response);
	}

}
