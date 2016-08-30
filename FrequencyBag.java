import java.util.ArrayList;

public class FrequencyBag{

ArrayList Bag = new ArrayList();
int bagSize = 0;

public int addWord(EtymWord w){

    //loop through the file and if something is found return
    for(int x = 0; x < Bag.size(); x++){
        
        EtymWord tempWord = new EtymWord();
        tempWord = (EtymWord) Bag.get(x);
        
        if(tempWord.getWord().equals(w.getWord())){
           tempWord.increaseOccurances();
            return 0;
        }
    
    }
bagSize++;
    Bag.add(w);
    return 1;

}

public void sortListAlphabetically(){
    int count = 0;
    int listIterator = 0;
    int bagsize = Bag.size();
    boolean listIsSorted = true;
    
    
    //creating a temprary list that will be sorted in
    ArrayList tempBag = new ArrayList();
    
    System.out.println("Sorting List");
//make sure list is sorted
    while(listIsSorted == false){

	while(listIterator < bagsize){
    		//make a tempWord
   		 EtymWord tempWord = new EtymWord();
		//create a temp nextword
    		EtymWord tempNextWord = new EtymWord();

		if(count < bagSize){
    			
    			tempWord = (EtymWord) Bag.get(count);


    			
    			tempNextWord = (EtymWord) Bag.get(count+1);
			}

    System.out.println("Tword: " + tempWord.toString());
     System.out.println("Temp: " + tempNextWord.toString());
     
    //using a simple bubble sort to sort the list
    
    //get the strings from the words
    String first = tempWord.getWord();
    String second = tempNextWord.getWord();
    //convert both strings to loserCase
    first = first.toLowerCase();
    second = second.toLowerCase();
    
    boolean swap = false;
    //loop through the 2 words and pull characters out
    for(int x = 0; x < first.length() && x < second.length(); x++){
        char firstChar = first.charAt(x);
        char secondChar = second.charAt(x);
        
        if(firstChar > secondChar){
            swap = false;
        }
        
        
        
        
    
    } 
	 if(swap == true){
        //to swap add the new values to the original bag
        Bag.add(count,tempNextWord);
	Bag.add(count+1,tempWord);
	listIsSorted = false;
    
    }

	listIterator++;
	

    }
   	listIterator = 0;
    
    
        count++;
    
    }


}

public String toString(){
    String output = "";
    for(int x = 0; x < Bag.size(); x++){
        EtymWord tempWord = new EtymWord();
        tempWord = (EtymWord) Bag.get(x);
    
    
        output = output + tempWord.getWord() + "    " + tempWord.getEtymology() + "   " + tempWord.getOccurances() + "\n";
    
    }
    return output;

}





}