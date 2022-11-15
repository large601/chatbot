import java.util.Arrays;
import java.util.ArrayList;

/**
 * A program to carry on conversations with a human user.
 * This version:
 *<ul><li>
 * 		Uses advanced search for keywords 
 *</li><li>
 * 		Will transform statements as well as react to keywords
 *</li></ul>
 * @author Laurie White
 * @version April 2012
 *
 */
public class Magpie4Refactored
{
	/**
	 * Get a default greeting 	
	 * @return a greeting
	 */	
	String name;
	int whichQuestion = 0;
	boolean isInterest = false;
	String lastResponse = "";
	boolean justGreeted;
	boolean doesDislike = false;
	ArrayList<String> likes = new ArrayList<String>();
	ArrayList<String> dislikes = new ArrayList<String>();
	String[] messiLikes = new String[] {"soccer", "scoring", "winning", "barcelona", "psg"};
	String[] messiDislikes = new String[] {"ronaldo", "losing", "real madrid", "speed"};
	String[] keywords = new String[] {"goals", "number", "old", "world cup", "born", "ronaldo"};
    int currState = 1;
    int responses = 0;

	public String getGreeting()
	{
		justGreeted = true;
		return "Hello, let's talk. What's your name?";
	}

    public int parseInput(String statement){
        if (findKeyword(statement, "name", 0) >= 0 || findKeyword(statement, "hi", 0) >= 0 || findKeyword(statement, "hello", 0) >= 0 || findKeyword(statement, "hi", 0) >= 0 || findKeyword(statement, "meet", 0) >= 0){
            currState = 1;
            return currState;
        }
        if (findKeyword(statement, "?", 0) >= 0 || findKeyword(statement, "what", 0) >= 0 || findKeyword(statement, "how", 0) >= 0){
            currState = 2;
            return currState;
        }
        if (findKeyword(statement, "goals", 0) >= 0 || findKeyword(statement, "number", 0) >= 0 || findKeyword(statement, "world cup", 0) >= 0 || findKeyword(statement, "old", 0) >= 0){
            currState = 3;
            return currState;
        }
        if (findKeyword(statement, "yes", 0) >= 0 || findKeyword(statement, "yeah", 0) >= 0 || findKeyword(statement, "no", 0) >= 0 || findKeyword(statement, "nah", 0) >= 0){
            currState = 4;
            return currState;
        }
        if (findKeyword(statement, "I like", 0) >= 0 || findKeyword(statement, "I love", 0) >= 0 || findKeyword(statement, "I hate", 0) >= 0 || findKeyword(statement, "I don't like", 0) >= 0){
            currState = 5;
            return currState;
        }
        else{
            currState = 0;
            return currState;
        }
        
    }

    public String setState(String statement, int currState){
        switch(currState){
            case 1:
                return greeting(statement);
            case 2:
                question(statement);
            case 3:
                onTopic(statement);
            case 4:
                return questionResponse(statement);
            case 5:
                return likeResponse(statement);
            default:
            System.out.println("yuh lol");
                return getRandomResponse();
        }
    }

    public String greeting(String statement){
        String response = "How's it going?";
        if (justGreeted == true){
			if (findKeyword(statement, "My name is", 0) >= 0){
				int startPos = findKeyword(statement, "My name is", 0) + 10;
				name = statement.substring(startPos).trim();
			}
			else{
				name = statement;
			}
			response = "Nice to meet you " + name + ", I'm Lionel Messi, striker for PSG, do you like Soccer?";
			justGreeted = false;
		}
        else if(justGreeted == false && responses == 0){
            response = getGreeting();
        }
        return response;
    }

    public String questionResponse(String statement){
        String response = getRandomResponse();
        if (findKeyword(statement.toLowerCase(), "no") >= 0)
		{
			if (findKeyword(lastResponse.toLowerCase(), "do you") >= 0){
				response = getResponse(transformDoYouQuestion(lastResponse, "no"));
			}
			else if (findKeyword(lastResponse.toLowerCase(), "are you") >= 0){
				response = getResponse(transformAreYouQuestion(lastResponse, "no"));
			}
			else{
				for (String word : keywords){
					if (findKeyword(statement, word, 0) >= 0){
						isInterest = true;
						if (word.equals("goals")){
							response  = "I've scored 785 goals throughout my career, probably more by now. 778 for FC Barcelona, 51 for PSG, and 164 for my home country, Argentina.";
						}
						else if (word.equals("world cup")){
							response = "I will be playing in the world cup starting on November 20 in Qatar for the Argentinian National Team.";
						}
						else if (word.equals("born") || (word.equals("old"))){
							response = "I am 35 years old, I was born on June 24, 1987 in Rosario, Argentina.";
						}
						else if (word.equals("number")){
							response = "When I played for Barcelona, I was number 10. Now that I am on PSG I wear number 30.";
						}
					}
				}
				if(isInterest == false){
					response = "Why so negative? " + getRandomQuestion();
				}
			}
		}
		else if (findKeyword(statement.toLowerCase(), "yes") >= 0 || findKeyword(statement.toLowerCase(), "yeah") >= 0 || findKeyword(statement.toLowerCase(), "yup") >= 0 || findKeyword(statement.toLowerCase(), "probably") >= 0 || findKeyword(statement.toLowerCase(), "maybe") >= 0)
		{
			if (findKeyword(lastResponse.toLowerCase(), "do you") >= 0){
				response = getResponse(transformDoYouQuestion(lastResponse, "yes"));
			}
			else if (findKeyword(lastResponse.toLowerCase(), "are you") >= 0){
				response = getResponse(transformAreYouQuestion(lastResponse, "yes"));
			}
			else{
				for (String word : keywords){
					if (findKeyword(statement, word, 0) >= 0){
						isInterest = true;
						if (word.equals("goals")){
							response  = "I've scored 785 goals throughout my career, probably more by now. 778 for FC Barcelona, 51 for PSG, and 164 for my home country, Argentina.";
						}
						else if (word.equals("world cup")){
							response = "I will be playing in the world cup starting on November 20 in Qatar for the Argentinian National Team. You should cheer for us.";
						}
						else if (word.equals("born") || (word.equals("old"))){
							response = "I am 35 years old, I was born on June 24, 1987 in Rosario, Argentina.";
						}
						else if (word.equals("number")){
							response = "When I played for Barcelona, I was number 10. Now that I am on PSG I wear number 30.";
						}
					}
				}
				if(isInterest == false){
					response = "Nice. " + getRandomQuestion();
				}
			}
		}
        return response;
    }

    public String likeResponse(String statement){
        String response = "Me too!";
        if (findKeyword(statement, "I like", 0) >= 0)
		{
			int startPos = findKeyword(statement, "I like", 0) + 6;
			likes.add(statement.substring(startPos).trim());
			String word = statement.substring(startPos).trim();
			doesDislike = DoesMessiDislike(word);
			if (doesDislike == false){
			response = "I like " + word + " too! " + getRandomPositiveDetails();
			}
			else{
				response = "Oh, I hate " + word + ". " + getRandomPositiveDetails();
				doesDislike = false;
			}
		}

		else if (findKeyword(statement, "I love", 0) >= 0)
		{
			int startPos = findKeyword(statement, "I love", 0) + 6;
			likes.add(statement.substring(startPos).trim());
			String word = statement.substring(startPos).trim();
			doesDislike = DoesMessiDislike(word);
			if (doesDislike == false){
			response = "I love " + word + " too! " + getRandomPositiveDetails();
			}
			else{
				response = "Oh, I hate " + word + ". " + getRandomPositiveDetails();
				doesDislike = false;
			}
		}

		else if (findKeyword(statement, "I don't like", 0) >= 0)
		{
			int startPos = findKeyword(statement, "I don't like", 0) + 12;
			dislikes.add(statement.substring(startPos).trim());
			String word = statement.substring(startPos).trim();
			doesDislike = DoesMessiLike(word);
			if (doesDislike == false){
				if (likes.size()>=1){
					int randIndex = (int) (Math.random() * likes.size());
					response = "I hate " + word + " too! " + "So you like " + likes.get(randIndex) + " but you don't like " + word + "?";
				}
				else{
					response = "I hate " + word + " too!" + getRandomQuestion();
				}
			}
			else{
				response = getRandomNegativeDetails() + " I love " + word + ". ";
				doesDislike = false;
			}
		}

		else if (findKeyword(statement, "I hate", 0) >= 0)
		{
			int startPos = findKeyword(statement, "I hate", 0) + 6;
			dislikes.add(statement.substring(startPos).trim());
			String word = statement.substring(startPos).trim();
			doesDislike = DoesMessiLike(word);
			if (doesDislike == false){
				if (likes.size()>=1){
					int randIndex = (int) (Math.random() * likes.size());
					response = "I hate " + word + " too! " + "So you like " + likes.get(randIndex) + " but you don't like " + word + "?";
				}
				else{
					response = "I hate " + word + " too!" + getRandomQuestion();
				}
			}
			else{
				response = getRandomNegativeDetails() + " I love " + word + ". ";
				doesDislike = false;
			}
		}
        return response;
    }

    public String question(String statement){
        String response = "Hmm. I'm not sure. Ask me something else";
        if (findKeyword(statement, "why", 0) >= 0)
		{
			response = "It's just true, best not to question it.";
		}
        for (String word : keywords){
            if (findKeyword(statement, word, 0) >= 0){
                if (word.equals("goals")){
                    response  = "I've scored 785 goals throughout my career, probably more by now. 778 for FC Barcelona, 51 for PSG, and 164 for my home country, Argentina.";
                }
                else if (word.equals("world cup")){
                    response = "I will be playing in the world cup starting on November 20 in Qatar for the Argentinian National Team.";
                }
                else if (word.equals("born") || (word.equals("old"))){
                    response = "I am 35 years old, I was born on June 24, 1987 in Rosario, Argentina.";
                }
                else if (word.equals("number")){
                    response = "When I played for Barcelona, I was number 10. Now that I am on PSG I wear number 30.";
                }
                else if (word.equals("ronaldo")){
                    response = "Oh, we don't talk about him. I'm better in every single way.";
                }
            }
        }
        return response;
    }

    public String onTopic(String statement){
        String response = "I love soccer. Ask me more about it.";
        for (String word : keywords){
            if (findKeyword(statement, word, 0) >= 0){
                if (word.equals("goals")){
                    response  = "I've scored 785 goals throughout my career, probably more by now. 778 for FC Barcelona, 51 for PSG, and 164 for my home country, Argentina.";
                }
                else if (word.equals("world cup")){
                    response = "I will be playing in the world cup starting on November 20 in Qatar for the Argentinian National Team.";
                }
                else if (word.equals("born") || (word.equals("old"))){
                    response = "I am 35 years old, I was born on June 24, 1987 in Rosario, Argentina.";
                }
                else if (word.equals("number")){
                    response = "When I played for Barcelona, I was number 10. Now that I am on PSG I wear number 30.";
                }
                else if (word.equals("ronaldo")){
                    response = "Oh, we don't talk about him. I'm better in every single way.";
                }
            }
        }
        return response;
    }

	
	/**
	 * Gives a response to a user statement
	 * 
	 * @param statement
	 *            the user statement
	 * @return a response based on the rules given
	 */
	public String getResponse(String statement)
	{
		String response = "";
        currState = parseInput(statement);

		if (statement.length() == 0)
		{
			response = "Say something, please.";
		}

		response = setState(statement, currState);
        if (responses == 0){
            response = greeting(statement);
        }
		lastResponse = response;
        responses++;
		return response;
	}
	
	/**
	 * Take a statement with "I want to <something>." and transform it into 
	 * "What would it mean to <something>?"
	 * @param statement the user statement, assumed to contain "I want to"
	 * @return the transformed statement
	 */
	private String transformIWantToStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		int psn = findKeyword (statement, "I want to", 0);
		String restOfStatement = statement.substring(psn + 9).trim();
		return "What would it mean to " + restOfStatement + "?";
	}

	private String transformDoYouQuestion(String question, String response)
	{
		question = question.toLowerCase();
		int DYIndex = question.indexOf("do you ");
		String statement = question.substring(DYIndex + 7, question.length()-1);
		if(response.equals("yes")){
			statement = "I " + statement;
		}
		else{
			statement = "I don't " + statement;
		}
		return statement;
	}

	private String transformAreYouQuestion(String question, String response)
	{
		question = question.toLowerCase();
		int DYIndex = question.indexOf("are you ");
		String statement = question.substring(DYIndex + 8, question.length()-1);
		if(response.equals("yes")){
			statement = "I am " + statement;
		}
		else{
			statement = "I am not " + statement;
		}
		return statement;
	}

	private boolean DoesMessiLike(String thing){
		for (String i : messiLikes){
			if (i.equals(thing)){
				return true;
			}
		}
		return false;
	}

	private boolean DoesMessiDislike(String thing){
		for (String i : messiDislikes){
			if (i.equals(thing)){
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * Take a statement with "you <something> me" and transform it into 
	 * "What makes you think that I <something> you?"
	 * @param statement the user statement, assumed to contain "you" followed by "me"
	 * @return the transformed statement
	 */
	private String transformYouMeStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		
		int psnOfYou = findKeyword (statement, "you", 0);
		int psnOfMe = findKeyword (statement, "me", psnOfYou + 3);
		
		String restOfStatement = statement.substring(psnOfYou + 3, psnOfMe).trim();
		return "What makes you think that I " + restOfStatement + " you?";
	}
	
	

	
	
	/**
	 * Search for one word in phrase.  The search is not case sensitive.
	 * This method will check that the given goal is not a substring of a longer string
	 * (so, for example, "I know" does not contain "no").  
	 * @param statement the string to search
	 * @param goal the string to search for
	 * @param startPos the character of the string to begin the search at
	 * @return the index of the first occurrence of goal in statement or -1 if it's not found
	 */
	private int findKeyword(String statement, String goal, int startPos)
	{
		String phrase = statement.trim();
		//  The only change to incorporate the startPos is in the line below
		int psn = phrase.toLowerCase().indexOf(goal.toLowerCase(), startPos);
		
		//  Refinement--make sure the goal isn't part of a word 
		while (psn >= 0) 
		{
			//  Find the string of length 1 before and after the word
			String before = " ", after = " "; 
			if (psn > 0)
			{
				before = phrase.substring (psn - 1, psn).toLowerCase();
			}
			if (psn + goal.length() < phrase.length())
			{
				after = phrase.substring(psn + goal.length(), psn + goal.length() + 1).toLowerCase();
			}
			
			//  If before and after aren't letters, we've found the word
			if (((before.compareTo ("a") < 0 ) || (before.compareTo("z") > 0))  //  before is not a letter
					&& ((after.compareTo ("a") < 0 ) || (after.compareTo("z") > 0)))
			{
				return psn;
			}
			
			//  The last position didn't work, so let's find the next, if there is one.
			psn = phrase.indexOf(goal.toLowerCase(), psn + 1);
			
		}
		
		return -1;
	}
	
	/**
	 * Search for one word in phrase.  The search is not case sensitive.
	 * This method will check that the given goal is not a substring of a longer string
	 * (so, for example, "I know" does not contain "no").  The search begins at the beginning of the string.  
	 * @param statement the string to search
	 * @param goal the string to search for
	 * @return the index of the first occurrence of goal in statement or -1 if it's not found
	 */
	private int findKeyword(String statement, String goal)
	{
		return findKeyword (statement, goal, 0);
	}
	


	/**
	 * Pick a default response to use if nothing else fits.
	 * @return a non-committal string
	 */
	private String getRandomResponse()
	{
		final int NUMBER_OF_RESPONSES = 4;
		double r = Math.random();
		int whichResponse = (int)(r * NUMBER_OF_RESPONSES);
		String response = "";
		
		if (whichResponse == 0)
		{
			response = "Interesting, tell me more. " + getRandomQuestion();
		}
		else if (whichResponse == 1)
		{
			response = "Hmmm. " + getRandomQuestion();
		}
		else if (whichResponse == 2)
		{
			response = "Do you really think so? " + getRandomQuestion();
		}
		else if (whichResponse == 3)
		{
			response = "You don't say. " + getRandomQuestion();
		}

		return response;
	}

	private String getRandomQuestion()
	{
		String response = "";
		
		if (whichQuestion == 0)
		{
			response = "Do you like PSG?";
		}
		else if (whichQuestion == 1)
		{
			response = "Do you know how many goals I've scored?";
		}
		else if (whichQuestion == 2)
		{
			response = "Are you going to watch the world cup?";
		}
		else if (whichQuestion == 3)
		{
			response = "Do you like Ronaldo?";
		}
		else if (whichQuestion == 4)
		{
			response = "Are you a fan of me?";
		}
		else{
			response = "I'm out of questions. Ask me some.";
		}
		whichQuestion++;
		return response;
	}

	private String getRandomPositiveDetails()
	{
		final int NUMBER_OF_RESPONSES = 4;
		double r = Math.random();
		int whichResponse = (int)(r * NUMBER_OF_RESPONSES);
		String response = "";
		
		if (whichResponse == 0)
		{
			response = "Why?";
		}
		else if (whichResponse == 1)
		{
			response = "What makes you like it?";
		}
		else if (whichResponse == 2)
		{
			response = "What's your favorite part?";
		}
		else if (whichResponse == 3)
		{
			response = "Who's your favorite player?";
		}

		return response;
	}

	private String getRandomNegativeDetails()
	{
		final int NUMBER_OF_RESPONSES = 4;
		double r = Math.random();
		int whichResponse = (int)(r * NUMBER_OF_RESPONSES);
		String response = "";
		
		if (whichResponse == 0)
		{
			response = "Since when?";
		}
		else if (whichResponse == 1)
		{
			response = "What makes you dislike it?";
		}
		else if (whichResponse == 2)
		{
			response = "Why don't you?";
		}
		else if (whichResponse == 3)
		{
			response = "How come?";
		}

		return response;
	}

}
