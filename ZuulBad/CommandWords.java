package ZuulBad;

/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kölling and David J. Barnes
 *
 * @version 2016.02.29
 */

public enum CommandWords
{
	GO, QUIT, HELP, LOOK, EAT, HINT;

    /**
     * Constructor - initialise the command words.
     */
    CommandWords()
    {
        // nothing to do at the moment...
    }


    /**
     * Print all valid commands to System.out.
     */
    public String showAll() {
    	StringBuilder commands_builder = new StringBuilder();
	
		for (CommandWords command : CommandWords.values()) {
			commands_builder.append(command + " ");
		}
		 String commands = commands_builder.toString();
		 return commands;
    }
}
