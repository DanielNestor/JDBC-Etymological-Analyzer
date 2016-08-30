			English Etymological Analyzer
				Daniel Nestor
				Alpha 1.0


	This application is written in Java and utilizes MySQL with the JDBC connector.
What this program does is reads in text files and parses out words from a piece of text
then prompts the user for information about the Etymology of the word whether it be
West-Germanic, Latin, North Germanic, Greek etc then stores that information in a
database. Words not of these etymologies will be printed in black and the user should
know to insert the etymology when prompted as above by only the etymologies mentioned
if it does not want to appear as other. The user will only be prompted for words when 
the word does not exist in the database. This could lead to possible inconsistencies
such as the word "arm" in english having both a West-Germanic and Latin etymology 
depending on the context. Solutions will be worked on in a later version to deal with such 
issues