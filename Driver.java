import java.sql.*;
import java.lang.*;
import java.io.*;
import java.util.*;
import java.util.Properties;
import java.util.Scanner;





public class Driver
{

//a public variable is required for when the query for the current word is done and 
//if the value is found in the database

	public static String database_etymology = "";

    //create a connection
   public static Connection publicConnection;


    //create a FrequencyBag that will be used by the other functions
 public static   FrequencyBag Wordlist = new FrequencyBag();


  // The JDBC Connector Class.
  private static final String dbClassName = "com.mysql.jdbc.Driver";

  // Connection string. emotherearth is the database the program
  // is connecting to. You can include user and password after this
  // by adding (say) ?user=paulr&password=paulr. Not recommended!
  private static final String CONNECTION =  "jdbc:mysql://127.0.0.1/Words";

  public static void main(String[] args) throws ClassNotFoundException,SQLException
  {
        connectToDatabase();
        //close the public connection
         publicConnection.close();
   
   }
    
    
    
public static void connectToDatabase() throws ClassNotFoundException,SQLException{
    String username,password_final = "";
    
  
    System.out.println(dbClassName);
    // Class.forName(xxx) loads the jdbc classes and
    // creates a drivermanager class factory
    Class.forName(dbClassName);

    // Properties for user and password. Here the user and password are both 'paulr'
    Properties p = new Properties();
    
    Scanner scan = new Scanner(System.in);
    //read the user info from the standard input:
    System.out.print("Please log into the Database to use the Application:\n\nUsername: ");
    username = scan.nextLine();
    
    //getting a password
    Console console = System.console() ;
    char [] password = console.readPassword("Enter password: ");
    for(int x = 0; x < password.length; x++){
        password_final = password_final + password[x];
    }
    
    //System.out.println(password_final);
    
    
    
    
    //put the username and password into the database
    p.put("user",username);
    p.put("password",password_final);

    // Now try to connect
    Connection connection1 = DriverManager.getConnection(CONNECTION,p);
    publicConnection = connection1;
    
    System.out.println("It works !");
    
    mainMenu(connection1);
    
    
    connection1.close();
    
    
    
    }
    
    
    //here is the main menu section of the application
    public static void mainMenu(Connection c1){
        System.out.println("welcome to the Daniel Nestor Etymology analyzer\n1: Create Table\n2: Drop Table\n3: Load File\n4: Sort Wordlist\n5: Display List\n6: Generate Html File \n7: Exit");
        Scanner scan = new Scanner(System.in);
        int x = scan.nextInt();
        
       // System.out.println("The Value of X is: " + x);
        if(x == 3){
             //once connected start doing things to the database
            readFile(c1);
           

		//return back to the main menu
		mainMenu(c1);
            
        }

	if(x == 4){
		//once the file is read sort the list alphabetically
           		Wordlist.sortListAlphabetically();
			mainMenu(c1);
	}
	if(x == 5){
		System.out.println(Wordlist.toString());
		mainMenu(c1);
	}
	if(x == 6){
		//generateHTML(c1);
		
	}
	//this is to exit
	if(x == 7){
		try{
			c1.close();}
			catch(SQLException e){

		}
		System.exit(0);
	}
        if(x == 1){
            createTable(c1);
        }
    
    	//if the user fails to decide just exit
    	System.exit(0);
    }


//generate an html tags to be written into the file
    public static String generateHTMLTag(EtymWord cur_word){
		String output_string = "";
		if(cur_word.getEtymology().equals("West-Germanic")){
			output_string = "<font face=\"Sans\" color=\"green\"> " + cur_word.getWord() + " </font>";
		}
		else if(cur_word.getEtymology().equals("North-Germanic")){
			output_string = "<font face=\"Sans\" color=\"blue\"> " + cur_word.getWord() + " </font>";
		}
		else if(cur_word.getEtymology().equals("Latin")){
			output_string = "<font face=\"Sans\" color=\"red\"> " + cur_word.getWord() + " </font>";
		}
		else if(cur_word.getEtymology().equals("Greek")){
			output_string = "<font face=\"Sans\" color=\"yellow\"> " + cur_word.getWord() + " </font>";
		}
		else{
			output_string = "<font face=\"Sans\" color=\"black\"> " + cur_word.getWord() + " </font>";
		}
		return output_string;

	}


    public static void createTable(Connection c1){
        System.out.println("Inside Create table");
    
        String query = "CREATE TABLE WORD_COLLECTION(WORD VARCHAR(20), ETYM VARCHAR(20))";
        
        //execute the query
        try{
            PreparedStatement stmt = c1.prepareStatement(query);
            stmt.executeUpdate(query);
           c1.close();
        System.out.println("Table Created");
        
        }catch(SQLException e ){
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        
        }
        
	mainMenu(c1);
    
    }
    
   //this function checks the word given and returns true or false based on whether or not it is in the database
public static boolean checkWord(EtymWord word,Connection c1){
	String query = "Select ETYM from WORD_COLLECTION where word = \"" + word.getWord() + "\"";
	int result_set_count = 0;
	//submit the query to see if there is a word with the same value in the database
	try{
		 PreparedStatement stmt = c1.prepareStatement(query);
            	ResultSet rs = stmt.executeQuery(query);

		//check the result set
		while(rs.next()){
			database_etymology = rs.getString("ETYM");
			result_set_count++;
		}

           //	c1.close();

	}
	catch(SQLException e){
	    System.err.println("Got an exception!");
            System.err.println(e.getMessage());

	}

	if(result_set_count == 0){
		return false;
	}
	
	return true;

}





    
  //function to read in a file and break it into some words
  public static void readFile(Connection c1){
        String file_name = "";
        String current_line = "";
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the name of the file you would like to open:");
	int html_line_count = 0;
        String tag = "";


        try{
        file_name = scan.nextLine();
        
        Scanner filescanner = new Scanner(new File(file_name));
        //read in the lines
        while(filescanner.hasNext()){
            current_line =  filescanner.nextLine() + " ";
            
            //break the string apart into words
            int index = 0;
            String word = "";
            
		//printing out the current line just to check
		System.out.println(current_line+"\n\n");

            while(index < current_line.length() - 1){

		//first single if statement
                //REMOVE THE PUNCTUATIONS   
                if(current_line.charAt(index) == ',' || current_line.charAt(index) == '.' || current_line.charAt(index) == '?' || current_line.charAt(index) == '!'){
                    System.out.println("Inside Remove Punct: " + word);
                   //move the index forward one
			index++;
		}
                 



		

                   
                    
                //if the end of a word is found(SECOND IF Statement)
                if(current_line.charAt(index) == ' ' && word.length() != 0){
                    //add the orignal word to a frequency bag

		//check to see if the word is already in the database
		//if it is set the etymology to that found in the database
		//if not ask the user for the etymology
		

			EtymWord newWord = new EtymWord(word, "Null", 1);
			//Nested if/else statement
			if(checkWord(newWord,c1) == true){
				
				System.out.println("CheckWord Returned true");
				//word was found in the database
				newWord.setEtymology(database_etymology);
			 	Wordlist.addWord(newWord);
                    		word = "";

				}
			else{

				
				System.out.println("The word " + newWord.getWord() + " was not found in the database could you please provide an etymology for it");
					Scanner scan2 = new Scanner(System.in);
				System.out.print("Please enter Germanic, Latin, Greek, Etc... : ");
						String etymNew = "";
						etymNew = scan2.nextLine();
						etymNew.toLowerCase();
					//add the word to the database
				    //EtymWord newWord = new EtymWord(word, "Germanic", 1);
					newWord.setEtymology(etymNew);


					//add the new word to the database
					String query = "INSERT INTO WORD_COLLECTION (word,etym) VALUES(\"" + newWord.getWord() + "\",\"" + newWord.getEtymology() + "\")";
					
					

					System.out.println("\n\n" + query + "\n\n");
					int result_set_count = 0;
					//submit the query to see if there is a word with the same value in the database
					try{
						 PreparedStatement stmt = c1.prepareStatement(query);
            					 stmt.executeUpdate(query);

		

           					//	c1.close();

						}
						catch(SQLException e){
	   						 System.err.println("Got an exception!");
            						System.err.println(e.getMessage());

									}









					//add the word to the word list
                     			 Wordlist.addWord(newWord);

					//clear the old word
                   			 word = "";
			}

			//generate a string with the html tag
					tag = tag + generateHTMLTag(newWord);
					//System.out.println("Html Tag: " + tag);
			//if the number mod 5 is 0 add a new line to the html file
			if(html_line_count % 5 == 0){
				tag = tag + "<br></br>\n";
			}

					html_line_count++;


                  
                }
                else{

			//inside this else the word continues to be built
                    word = word + current_line.charAt(index);
                    System.out.println(word);
                }
            
            
            index++;
            
            }
            
            
            }
            
        }catch(Exception e){
            System.out.println("Exception Found");
        }

	//add tags to the tag
	tag = "<html>" + tag + "</html>";
  
  	
		

	
	try {
		Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Output.html"), "UTF-16"));
    		out.write(tag);
		out.close();
	} catch(Exception e) {
   		 //out.close();
		}

  	
  
  }
    
    
    
}