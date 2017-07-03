
package hangman;

import java.util.*;

public class HangmanCheat implements HangmanManager
{

    private String pattern = "";
    private int max;
    private int length;
    private SortedSet<Character> guessesMade;
    private Set<String> currentWords;
    private Map<String, Set<String>> patternMap;
    private Set<String> words = new TreeSet<String>(); 

    public HangmanCheat(List<String> dictionary , int length, int max)
    {
        this.max = max;
        this.length = length;


        if( length < 1 && max < 0)
            {
                throw new IllegalArgumentException();

            }

        words = new TreeSet<String>(); 
        guessesMade = new TreeSet<Character>();
        currentWords = new TreeSet<String>(); // current words =(words)
        patternMap = new TreeMap<String, Set<String>>(); // patternMAP = < pattern, words>

        for (String word : dictionary)
        {
            if (word.length() == length)
            {
            words.add(word); // if length of the word matches a word with the same length it will  be added
            }

        }

    }

    @Override
    public int wrongGuessLimit() {
        return max;
    }

    public Set<String> words()
    {
        return words;
    }


    public int guessesLeft()
    {
        return max - guessesMade.size();
    }



    public SortedSet<Character> guesses()
    {
        return guessesMade;
    }


    public String pattern()
    {
        if (words.isEmpty())
        {
            throw new IllegalArgumentException("Invalid No Words");
        }
        pattern = " "; // blank for now
        for (int i = 0; i < length; i++)
        {
            pattern += "-"; // will have a "-" for how long the length of the word is 
        }

        return pattern; // will return the number of lines
    }


    public int record(char guess)
    {
          if (guessesLeft() < 1 || words.isEmpty())
          {
                throw new IllegalStateException();  
          }

          if (!words.isEmpty() && guessesMade.contains(guess)) 
          {
                throw new IllegalArgumentException();
          }

          guessesMade.add(guess); // guess
          int occurences = 0;

          for( String word: words)
          {           
              if( patternMap.containsKey (pattern))
              {

                    occurences = generatePattern(word, guess); // the word including the guess letter will fill in the blank spots
                    currentWords.add(word); // the word will be added to the possibilities 
                    currentWords = patternMap.get(pattern); // the word will be able to fill once the guesses are made
                  if(patternMap.get(pattern)!=null)
                      {
                          currentWords = patternMap.get(pattern);
                          
                      }
                    patternMap.put(pattern, currentWords);  
              }
              else
              {
                 currentWords.add(word);
                 patternMap.put(pattern, currentWords);
              } 
          }

         words = find();
         List<String> output = new ArrayList<>(words);
         for(int i = 0; i < output.size(); i++){
             System.out.println(output.get(i));
         }
         return occurences;
    }


    private Set<String> find()
    {
        int maxSize = 0;

        Map <String, Integer> patternCount = new TreeMap<String, Integer>();

        for (String key : patternMap.keySet()) // keyset equals word
        {
            patternCount.put(key, patternMap.get(key).size()); // size of the word

                if (patternMap.get(key).size() > maxSize)
                {
                    maxSize = patternMap.get(key).size();
                    pattern = key; // pattern will becomes based on the word
                } else if (patternMap.get(key).size() == maxSize)
                {
                    if (key.length() >= pattern.length())
                    {
                        pattern = key;
                        maxSize = patternMap.get(key).size(); // the pattern is now the word key 
                    }
                }
            }
        System.out.println("Current pattern: " + pattern);

        return patternMap.get(pattern); // the pattern that will becomes now that the word was picked
    }

    private int generatePattern(String s, char guess) 
    {
        int count = 0;
        pattern = "";
            for (int i = 0; i < length; i++)
            {
                if (s.charAt(i) == guess)
                {
                    pattern += guess + " ";
                    count++;
                } else 
                {
                    pattern += "- ";
                }
            }
        return count;
    }

    

}
