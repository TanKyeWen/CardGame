import java.util.Random;
import java.util.Scanner;

public class MainApp{
    private static final String[] cards = new String[]{"2@", "2#", "2^", "2*", "3@", "3#", "3^", "3*", "4@", "4#", "4^", "4*", "5@", "5#", "5^", "5*",
                                                "6@", "6#", "6^", "6*", "7@", "7#", "7^", "7*", "8@", "8#", "8^", "8*", "9@", "9#", "9^", "9*",
                                                "10@", "10#", "10^", "10*", "J@", "J#", "J^", "J*", "Q@", "Q#", "Q^", "Q*", "K@", "K#", "K^", "K*",
                                                "A@", "A#", "A^", "A*"};
    public static void main(String[] args) throws InterruptedException{
        boolean gameState = true;
        int gameProgression = 1;
        Scanner scan = new Scanner(System.in);

        //To loop through the entire game
        while(gameState){
            String[] playerNames = new String[4];

            //Welcome screen
            if(gameProgression == 1){
                System.out.println("--------------------------------------------------------" + 
                                    "\n\tWELCOME TO WINNER TAKES ALL CARD GAME" +
                                    "\n--------------------------------------------------------"+
                                    "\nEnter Player 1 name : ");
                
                for(int i = 1; i < 5; i++){
                    String playerName = scan.nextLine();
                    clearScreen();
                    playerNames[i-1] = playerName;
                    System.out.println("--------------------------------------------------" + 
                                    "\nWELCOME TO WINNER TAKES ALL CARD GAME" +
                                    "\n--------------------------------------------------"+
                                    "\nEnter Player " + (i+1) + " name : ");
                }
                gameProgression = 2;
            }

            if(gameProgression == 2){
                //Initialize players
                Players player1 = new Players(playerNames[0]);
                Players player2 = new Players(playerNames[1]);
                Players player3 = new Players(playerNames[2]);
                Players player4 = new Players(playerNames[3]);
                //Display shuffled cards
                clearScreen();

                try{
                    System.out.println("Cards are shuffling...");
                    Thread.sleep(100);
                    
                    clearScreen();
                    String[] shuffledCards = cardShufflier();
                    System.out.println("Shuffled Cards : ");
                    for(String card : shuffledCards){
                        System.out.print("|" + card + "| ");
                        Thread.sleep(250);
                    }
                    enterToContinue(scan);

                    //Give players their cards
                    Players[] players = {player1, player2, player3, player4};
                    for (int i = 0; i < shuffledCards.length; i++) {
                        players[i % 4].setDeck(shuffledCards[i], i / 4);
                    }

                    System.out.println("Giving out cards to players...\nPlease wait....");
                    Thread.sleep(2000);
                    System.out.println("DONE!");
                    enterToContinue(scan);
                    clearScreen(); 

                    //Print out player's cards
                    System.out.println("\n\nPlayer " + player1.getPlayerName() + " : ");
                    player1.showPlayerDeck();
                    Thread.sleep(2500);

                    System.out.println("\nPlayer " + player2.getPlayerName() + " : ");
                    player2.showPlayerDeck();
                    Thread.sleep(2500);

                    System.out.println("\nPlayer " + player3.getPlayerName() + " : ");
                    player3.showPlayerDeck();
                    Thread.sleep(2500);

                    System.out.println("\nPlayer " + player4.getPlayerName() + " : ");
                    player4.showPlayerDeck();
                    Thread.sleep(2500);

                    System.out.print("Please view the card.....");
                    
                    //Count player scores
                    int[] playersScore = new int[]{countScore(player1, cards), countScore(player2, cards), countScore(player3, cards), countScore(player4, cards)};
                    int playerIndex = -1, max = Integer.MIN_VALUE;
                
                    for(int i = 0; i < playersScore.length; i++){
                        if (playersScore[i] > max) {
                            max = playersScore[i];
                            playerIndex = i;
                        }
                    }
                    
                    //Set winners
                    String[] winningPlayerDeck = new String[13];
                    String playerName = "";

                    switch (playerIndex) {
                        case 0:
                            winningPlayerDeck = player1.getPlayerDeck();
                            playerName = player1.getPlayerName();
                            break;
                        case 1:
                            winningPlayerDeck = player2.getPlayerDeck();
                            playerName = player2.getPlayerName();
                            break;
                        case 2:
                            winningPlayerDeck = player3.getPlayerDeck();
                            playerName = player3.getPlayerName();
                            break;
                        case 3:
                            winningPlayerDeck = player4.getPlayerDeck();
                            playerName = player4.getPlayerName();
                            break;
                    }

                    enterToContinue(scan);
                    clearScreen();
                    System.out.println("Calculating winner.......\nPlease Wait....");
                    Thread.sleep(100);

                    clearScreen();
                    System.out.print("Winner: Player " + playerName + "\nPlayer Deck: \n");
                    for(String card : winningPlayerDeck){
                        System.out.print("|" + card + "| ");
                    }
                    enterToContinue(scan);
                    clearScreen();
                    
                    gameProgression = 3;

                }catch(InterruptedException e){
                    System.out.println("error");
                    gameProgression = 4;

                }
            }

            if(gameProgression == 3){
                boolean endState = true;

                //To select if the players wants to replay or end the game
                while (endState) {
                    clearScreen();
                    System.out.println("Do you want to replay?");
                    System.out.println("1. Yes");
                    System.out.println("2. No");
                    System.out.print("Enter your choice: ");
                    String replayChoice = scan.nextLine();

                    if(replayChoice.equals("1")){
                        gameProgression = 1;
                        endState = false;
                        clearScreen();

                    }else if(replayChoice.equals("2")){
                        gameProgression = 4;
                        endState = false;
                        clearScreen();

                    }else{
                        System.out.println("Invalid Choice\nRedirecting to Replay selector.");
                        Thread.sleep(2500);
                        clearScreen();

                    }
                }
            }

            if(gameProgression == 4){
                System.out.println("Thank you for playing! Come back soon! :D\nGame Shutting Down......");
                Thread.sleep(2500);
                gameState = false;
            }
        }
    }

    public static String[] cardShufflier(){
        String[] newDeck = cards.clone();
        Random rand = new Random();
        
        //Shuffle cards randomly in shuffledCards array
        for (int i = newDeck.length-1; i > 0; i--) {
            int randomIndexToSwap = rand.nextInt(newDeck.length);
            String temp = newDeck[randomIndexToSwap];
            newDeck[randomIndexToSwap] = newDeck[i];
            newDeck[i] = temp;
        }

        return newDeck;
    }

    public static int indexOf(String[] array, String str){
        int index = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(str)) {
                index = i;
            }
        }

        return index;
    }

    public static int countScore(Players player, String[] array){
        String[] playerDeck = player.getPlayerDeck();
        int score = 0;

        for(int i = 0; i < playerDeck.length; i++){
            score += indexOf(array, playerDeck[i]);
        }

        return score;
    }

    public static void clearScreen(){
        System.out.print("\033[H\033[2J");  
        System.out.flush();
    }

    public static void enterToContinue(Scanner scan){
        System.out.println("\n\nPress any key to continue.......");
        scan.nextLine();
    }
}