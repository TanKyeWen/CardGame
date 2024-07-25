public class Players {
    private String playerName;
    private String[] playerDeck;

    public Players(String name){
        playerName = name;
        playerDeck = new String[13];
    }

    public void setPlayerName(String newPlayerName){
        playerName = newPlayerName;
    }

    public String getPlayerName(){
        return playerName;
    }

    public void setDeck(String card, int index){
        playerDeck[index] = card;
    }

    public String[] getPlayerDeck(){
        return playerDeck;
    }

    public void showPlayerDeck(){
        for(String card : playerDeck){
            System.out.print("|" + card + "| ");
        }
        System.out.println("");
    }
}
