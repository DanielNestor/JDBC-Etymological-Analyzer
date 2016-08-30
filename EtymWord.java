public class EtymWord{

String word = "";
String etymology = "";
int occurances = 1;

public EtymWord(){

}

public EtymWord(String w, String ety,int occ){
    word = w;
    etymology = ety;
    occurances = occ;
}

public void increaseOccurances(){
    occurances++;
}

public String getWord(){
    return word;
}
public String getEtymology(){
    return etymology;

}

public void setEtymology(String e){
	etymology = e;
}

public int getOccurances(){
    return occurances;

}

public String toString(){

    return "Word: " + word + "   Etymology: "  + etymology + "  Occurances:  " + occurances + "\n";
}


}
