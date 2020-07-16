import java.util.Scanner;
import java.io.*;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Fprojectp1
{
  public static void main (String[]args)throws IOException
  {
    boolean repeat = true;
    Scanner keyboard = new Scanner(System.in);
    while(repeat==true)
    {
      // read all info into 2d array
      File myFile = new File("userInfo.txt");
      Scanner inputFile0 = new Scanner(myFile);
      String[][]userInfo=new String[25][6];
      int row=0;
      while(inputFile0.hasNext())
      {
        String [] userLine=inputFile0.nextLine().split("\t");
        
        for(int col=0;col<6;col++) 
        {
          userInfo[row][col]=userLine[col];
        }
        row++;
      }
      inputFile0.close();
      
      //let user sign in & allow only 3 attempts
      boolean flag1 = false;
      boolean flag2 = true;
      int chances = 1;
      
      while(chances < 4 && flag2)
      {
        System.out.println("Please enter your username");
        String username =  keyboard.nextLine();
        System.out.println("Please enter your password");
        String password= keyboard.nextLine();
        int rowCount = 0;
        for (int row1=0;row1<userInfo.length;row1++)
        {
          for(int  col=0; col<userInfo[row1].length;col++)
          {
            if(username.equals(userInfo[row1][ col]) && password.equals((userInfo[row1][ col+1])))
            {
              rowCount = row1;
              flag1=true;
            }
          }
        }
        
//check if person is student or instructor
        //if instructor go to instructor mode
        if("Instructor".equals(userInfo[rowCount][5]))
        {
          System.out.println("Welcome "+ username);
          System.out.println("Choose intructor option");
          System.out.println("Enter \"1\" to register a new student," +
                             " Enter \"2\" to dispaly stats," + " Enter \"3\" to add new questions");
          int option = keyboard.nextInt();
          while(option>4 && option <0)
          {
            System.out.println("Error. Incorrect input. Enter \"1\" to register a new student," +
                               " Enter \"2\" to dispaly stats," + " Enter \"3\" to add new questions.");
            option = keyboard.nextInt();
          }
          if(option ==1)
          {
            System.out.println("Enter student's first name.");
            String addedFirstName = keyboard.nextLine();
            addedFirstName = keyboard.nextLine();
            System.out.println("Enter student's last name.");
            String addedLastName = keyboard.nextLine();
            System.out.println("Enter student's username.");
            String addedUserName = keyboard.nextLine();
            System.out.println("Enter student's email.");
            String addedEmail = keyboard.nextLine();
            String addedRole = "Student";
            String randPass = "ABCDEFGHIJKLNMOPQRSTUVWXYZ";
            Random rand = new Random();
            StringBuilder newPassword = new StringBuilder();
            String addedPass = null;
            for(int k =0; k < 7;k++)
            {
              int i = rand.nextInt(26)+1;
              newPassword.append(randPass.charAt(i));
              addedPass = newPassword.toString();
            }
            FileWriter fw = new FileWriter("userInfo.txt", true);
            PrintWriter outputFile = new PrintWriter(fw);
            outputFile.println("\n");
            outputFile.println
              (addedUserName + "\t" + addedPass + "\t" + addedFirstName + "\t" + addedLastName + "\t" + addedEmail + "\t" + addedRole);
            outputFile.close();
            fw.close();
          }
          else if(option==3)
          {
            System.out.println("Type \"1\" to enter question and answer manually "
                                 + ",Type \"2\" to upload your question and answer");
            int qA = keyboard.nextInt();
            
            if (qA==1)
            {
              // Question
              System.out.println("Enter Question: ");
              Scanner scan = new Scanner(System.in);
              String text = scan.nextLine();
              scan.close();
              FileWriter fWriter = null;
              BufferedWriter writer = null;
              fWriter = new FileWriter("TestBank.txt",true);
              writer = new BufferedWriter(fWriter);
              writer.write(text);
              writer.newLine();
              writer.close();
              System.out.println("The Question was added.");
              // Answer
              System.out.println("Enter Answer: ");
              text = scan.nextLine();
              fWriter = null;
              writer = null;
              fWriter = new FileWriter("Answers.txt",true);
              writer = new BufferedWriter(fWriter);
              writer.write(text);
              writer.newLine();
              writer.close();
              System.out.println("The Answer was added.");
            }
            if (qA==2)
            {
              System.out.println("Enter file name of questions to be added. Use \".txt\"");
              String qFileName = keyboard.nextLine();
              qFileName = keyboard.nextLine();
              File file2 = new File (qFileName);
              Scanner inputFile2 = new Scanner(file2);
              while(inputFile2.hasNext())
              {
                String line2 = inputFile2.nextLine();
                System.out.println(line2);
              }
              
              
            }
          }
          flag2=false;
        }
        else
        {
          if(flag1==true)
          {
            System.out.println("Welcome "+ username);
            
            System.out.println();
            System.out.println("Please answer True or False,\\(including T or F\\) ");
            System.out.println();
            
            //Questions into an Array
            int addedLines = 0;
            final int SIZE = 125 + addedLines;
            String[] questions = new String[SIZE]; 
            int i = 0;
            File file = new File ("TestBank.txt");
            Scanner inputFile = new Scanner(file);
            while (inputFile.hasNext() && i < questions.length)
            {
              questions[i] = inputFile.nextLine();
              i++;
            }
            inputFile.close();
            
//Answers from file into an Array
            String[] answersCorrect = new String[SIZE]; 
            i = 0;
            File file1 = new File ("Answers.txt");
            Scanner inputFile1 = new Scanner(file1);
            while (inputFile1.hasNext() && i < answersCorrect.length)
            {
              answersCorrect[i] = inputFile1.nextLine();
              i++;
            }
            inputFile1.close();
            
//Ask 10 Questions and Allows User to Answer 
            Random rand = new Random();
            String answersUser[] = new String[10];
            int line = rand.nextInt(125)+1;
            int lines[] = new int[SIZE];
            int points = 0;
            int linesStored[] = new int[10];
            long tStart = 0;
            for(i = 0; i< 10;)
            {
              for(int j=0; j <= i;j++)//checks to make sure questions only asked once
              {
                if(lines[j] == line)
                  line = rand.nextInt();
              }
              linesStored[i] = line; //stores line number used in each Q/A
              tStart = System.currentTimeMillis(); //Start time
              System.out.println(questions[line]);
              answersUser[i] = keyboard.nextLine();
              if((answersCorrect[line].equalsIgnoreCase(answersUser[i]))||
                 (Character.toString(answersCorrect[line].charAt(0)).equalsIgnoreCase(answersUser[i]))||
                 (Character.toString(answersCorrect[line].charAt(0)).equalsIgnoreCase(answersUser[i])))
              {
                points++;
              }
              if(("True".equalsIgnoreCase(answersUser[i]))||("False".equalsIgnoreCase(answersUser[i]))//makes sure answer accepts mult. forms
                   ||("T".equalsIgnoreCase(answersUser[i]))||("F".equalsIgnoreCase(answersUser[i])))
              {
                i++;
              }
              else
              {
                System.out.println("Error. Must use \"True\" or \"False\"");
                answersUser[i] = keyboard.nextLine();
              }
              line = rand.nextInt(125)+1;
            }
            
//Stores every answers line number
            int a1=linesStored[0];
            int a2=linesStored[1];
            int a3=linesStored[2];
            int a4=linesStored[3];
            int a5=linesStored[4];
            int a6=linesStored[5];
            int a7=linesStored[6];
            int a8=linesStored[7];
            int a9=linesStored[8];
            int a10=linesStored[9];
            System.out.println
              ("---------------------------------------------------");
            System.out.println("User Answer: " + answersUser[0]);
            System.out.println("Correct Answer: " + answersCorrect[a1]);
            System.out.println("User Answer: " + answersUser[1]);
            System.out.println("Correct Answer: " + answersCorrect[a2]);
            System.out.println("User Answer: " + answersUser[2]);
            System.out.println("Correct Answer: " + answersCorrect[a3]);
            System.out.println("User Answer: " + answersUser[3]);
            System.out.println("Correct Answer: " + answersCorrect[a4]);
            System.out.println("User Answer: " + answersUser[4]);
            System.out.println("Correct Answer: " + answersCorrect[a5]);
            System.out.println("User Answer: " + answersUser[5]);
            System.out.println("Correct Answer: " + answersCorrect[a6]);
            System.out.println("User Answer: " + answersUser[6]);
            System.out.println("Correct Answer: " + answersCorrect[a7]);
            System.out.println("User Answer: " + answersUser[7]);
            System.out.println("Correct Answer: " + answersCorrect[a8]);
            System.out.println("User Answer: " + answersUser[8]);
            System.out.println("Correct Answer: " + answersCorrect[a9]);
            System.out.println("User Answer: " + answersUser[9]);
            System.out.println("Correct Answer: " + answersCorrect[a10]);
            System.out.println();
            System.out.println("Firstname and Lastname " + userInfo[rowCount][2] + " " + userInfo[rowCount][3]);
            long tEnd = System.currentTimeMillis();
            long timeElapsed = tEnd - tStart;
            double seconds = (timeElapsed/100.0);
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("MM//dd//yyyy");
            System.out.println();
            System.out.println("Elapsed time: " + seconds+ " seconds");
            System.out.println();
            System.out.println("The date is: "+ formatter.format(date));
            System.out.println("Test Score is: " + points + "/10");
            
            
            PrintWriter outputFile = new PrintWriter("userName_COSC_236_Quiz_Date_Time.txt");
            outputFile.println("User Answer: " + answersUser[0]);
            outputFile.println("Correct Answer: " + answersCorrect[a1]);
            outputFile.println("User Answer: " + answersUser[1]);
            outputFile.println("Correct Answer: " + answersCorrect[a2]);
            outputFile.println("User Answer: " + answersUser[2]);
            outputFile.println("Correct Answer: " + answersCorrect[a3]);
            outputFile.println("User Answer: " + answersUser[3]);
            outputFile.println("Correct Answer: " + answersCorrect[a4]);
            outputFile.println("User Answer: " + answersUser[4]);
            outputFile.println("Correct Answer: " + answersCorrect[a5]);
            outputFile.println("User Answer: " + answersUser[5]);
            outputFile.println("Correct Answer: " + answersCorrect[a6]);
            outputFile.println("User Answer: " + answersUser[6]);
            outputFile.println("Correct Answer: " + answersCorrect[a7]);
            outputFile.println("User Answer: " + answersUser[7]);
            outputFile.println("Correct Answer: " + answersCorrect[a8]);
            outputFile.println("User Answer: " + answersUser[8]);
            outputFile.println("Correct Answer: " + answersCorrect[a9]);
            outputFile.println("User Answer: " + answersUser[9]);
            outputFile.println("Correct Answer: " + answersCorrect[a10]);
            outputFile.println();
            outputFile.println("Firstname and Lastname: " + userInfo[rowCount][2] + " " + userInfo[rowCount][3]);
            outputFile.println();
            outputFile.println("Elapsed time: " + seconds+ " seconds");
            outputFile.println();
            outputFile.println("The date is: "+ formatter.format(date));
            outputFile.println("Test Score is: " + points + "/10");
            outputFile.println();
            outputFile.close();
            flag2 = false;
          }
          else
          {
            chances++;
          }
        } 
      }
      System.out.println("Would you like to retake test with another username and password? Enter Yes or Done.");
      String cycle = keyboard.nextLine();
      if(("Yes".equalsIgnoreCase(cycle)))
        repeat = true;
      else if(("Done".equalsIgnoreCase(cycle)))
        repeat = false;
      else
      {
        System.out.println("Error. Incorrect choice, goodbye.");
        repeat = false;
      }
      
      
    }
    keyboard.close();
  }              
} 