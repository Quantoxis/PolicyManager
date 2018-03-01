package policymanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
/*
 * @author p4053489 Policy Manager prototype program
 */
public class PolicyManager
{
    public static void main(String[] args)
    {
        //method to display the menu
        displayMenu();
       
        Scanner input = new Scanner(System.in);
        int option = input.nextInt();
        
        while(option < 0 || option > 4)
        {
            //method to display the menu
            displayMenu();
            
            option = input.nextInt();
        }
        if(option == 1)
        {
            createPolicy();
        }
        else if(option == 2)
        {
           
            summaryPolicy();
        }
        else if(option == 3)
        {
            option3();
        }
        else if(option == 4)
        {
            option4();
        }
        else if(option == 0)
        {
            exit();
        }
    }
    //method to create menu
    static void displayMenu()
    {
        System.out.println("Please select an option:");
        System.out.println("1: Enter new Policy");
        System.out.println("2: Summary of Policies");
        System.out.println("3: Display Summary of Policies for Selected month");
        System.out.println("4: Find and display Policy");
        System.out.println("0: Exit");
    }
    
    static void createPolicy()
    {
         //configure methods
        String clientName = getClientName();
        String refNum = getRefNum();
        int noOfGadgets = getNoOfGadgets();
        int gadgetValue = getGadgetValue();
        int basicMonthlyPremium = getBasicMonthlyPremium(noOfGadgets, gadgetValue);
        boolean defaultExcess = getDefaultExcess();
        double excessValidation = getExcessValidation(defaultExcess);
        double newMonthlyPremium = getNewMonthlyPremium(excessValidation, basicMonthlyPremium, defaultExcess); 
        double basicAnnualPremium = getBasicAnnualPremium(newMonthlyPremium);
        boolean annualPaymentOption = getAnnualPaymentOption();
        double newAnnualPremium = getNewAnnualPremium(annualPaymentOption, basicAnnualPremium);
        double premium = getPremium(annualPaymentOption, newMonthlyPremium, newAnnualPremium);
        int premiumPence = getPremiumPence(premium);
        String surname = getSurname(clientName);
        String firstInitial = getFirstInitial(clientName);
        String abbrevClientName = getAbbrevClientName(firstInitial, surname);
        String date = getDate();
        String displayCover = getDisplayCover(annualPaymentOption);
        String dateFormat = getDateFormat(date);
        String uppercaseRef = getUppercaseRef(refNum);
        String displayItems = getDisplayItems(noOfGadgets);
        char paymentTerms = getPaymentTerms(annualPaymentOption);
        getSummaryMethod(abbrevClientName, dateFormat, uppercaseRef, displayCover,
                displayItems, excessValidation, premium, gadgetValue);
      
        appendToFile(date, uppercaseRef, noOfGadgets, gadgetValue, excessValidation, premiumPence, paymentTerms, clientName);
        int rowCountPolicy = getRowCountPolicy();
        int rowCountArchive = getRowCountArchive();
        
    }
    
    static void summaryPolicy()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Show summary of policy.txt or archive.txt? Enter policy or archive");
        String selection = input.next();
        selection.toLowerCase();
        while(!"policy".equals(selection) && !"archive".equals(selection))
        {
            System.out.println("Please enter policy or archive");
            selection = input.next();
            selection.toLowerCase();
        }
        if("policy".equals(selection))
        {
            try
            {
                FileReader file = new FileReader("policy.txt");
                BufferedReader buffer = new BufferedReader(file);
                String s = "";
                while((s = buffer.readLine()) != null)
                {
                    System.out.println(s);
                    System.out.println("Total number of policies: " + getRowCountPolicy());
                }
                buffer.close();
                   
            }
            catch(IOException error)
            {
                System.out.println("File could not be read");
            }
        }
        else if("archive".equals(selection))
        {
            try
            {
                FileReader file = new FileReader("archive.txt");
                BufferedReader buffer = new BufferedReader(file);
                String s = "";
                while((s = buffer.readLine()) != null)
                {
                    System.out.println(s);
                    
                }
                System.out.println("Total number of policies: " + getRowCountArchive());
                buffer.close();
                   
            }
            catch(IOException error)
            {
                System.out.println("File could not be read");
            }
        }
      
    }
    

    
    static String getPolicySummary(String clientName)
    {
      Policy name = new Policy();
      Policy dateFormat = new Policy();
      Policy excess = new Policy();
      Policy gadg = new Policy();
      name.getClientName();
      name.setClientName(clientName);
      dateFormat.getDate();
      excess.getExcessValidation();
      gadg.getNoOfGadgets();
      
      String s = " " + name.getClientName() + " " + name.getDate() + " " + name.getExcessValidation() + " " + name.getNoOfGadgets();
      name.setClientName(clientName);
      System.out.println(s);
    return s;
    
    }
    
    static int getRowCountPolicy()
    {
        int rows = 0;
        try
        {
        FileReader file = new FileReader("policy.txt");
        BufferedReader buffer = new BufferedReader(file);
        String s = "";
        while((s = buffer.readLine()) != null)
        {
            Scanner input = new Scanner(s);
            input.nextLine();
            rows++;
            input.hasNextLine();
           
        }
        buffer.close();
        }
       
        catch(IOException error)
        {
            System.out.println("");
        }
        return rows;
    }
    
    static int getRowCountArchive()
    {        
        int rows = 0;
        try
        {
        FileReader file = new FileReader("archive.txt");
        BufferedReader buffer = new BufferedReader(file);
        String s = "";
            while((s = buffer.readLine()) != null)
            {
                Scanner input = new Scanner(s);
                input.nextLine();
                rows++;
                input.hasNextLine();
           
            }
        buffer.close();
        }
       
        catch(IOException error)
        {
            System.out.println("");
        }
        return rows;
    }
    
    
    
    static void appendToFile(String date, String uppercaseRef, int noOfGadgets, int gadgetValue, 
            double excessValidation, int premiumPence, char paymentTerms, String clientName)
    {
        //create new text file named summary
        PrintWriter output = null;
        //create new summary folder
        File summary = new File("policy.txt");
        //if file does not exist create a new one
        if(!summary.exists())
        {
            try 
            {
                output = new PrintWriter(summary);
                
            }
            catch(FileNotFoundException error) 
            {
                //display error message
                System.out.println("Problem creating file! Exiting program.");
                System.exit(0); //exits program
            }
            System.out.println("Summary of contract entered");
      
            //append variables entered in contract to a raw text file.
            output.append(date + " " + uppercaseRef + " " + noOfGadgets + " " + gadgetValue + " " + (int)excessValidation + " " + 
                    + premiumPence + " " + paymentTerms + " " + clientName);
                       
            output.close(); 
        }
        //display error message if file does not exist.
        else
            {
                    System.out.println("This file already exists! Exiting program.");
            }
     }
    
    static void option3()
    {
        
    }
    static void option4()
    {
        
    }
    //method to quit program
    static void exit()
    {
        System.out.println("Exiting program");
        System.exit(0);
    }
    
    static String getClientName()
    {

        //create scanner input in to get the client name as a string
        Scanner input = new Scanner(System.in);

        //prompt user to enter name and give example of a valid input
        System.out.println("Please enter the client's name "
                + "e.g. Arthur Charles Clarke: ");

        //create name as a string and assign scanner input to name
        //using .nextLine() method instead of .next() to include whitespace characters
        String name = input.nextLine();
        //display error message if name is not entered or is too long
        while (name.length() > 25 || name.length() == 0)
        {

            if (name.length() == 0)
            {
                System.out.println("Name cannot be blank!");
            } else if (name.length() > 25)
            {
                System.out.println("Name cannot be over 25 characters including spaces.");
            }
            //get name again
            name = input.nextLine();

        }
        System.out.println("You have entered a valid name");

        //return name as clientName
        return name;
    }

    static String getRefNum()
    {
        //create scanner
        Scanner input = new Scanner(System.in);
        //prompt user
        System.out.println("Please enter the six digit reference number in the following format"
                + " AA123B ");

        //create constructor
        String ref = input.next();
        
        //check reference number is 6 characters long
        while(ref.length() != 6)
            {
                System.out.println("Check that reference number is 6 characters long.");
                ref = input.next();
            }
        
        //if reference number is 6 characters long this loop is entered into that checks the format
        //the program crashes and displays and error message if the number is not 6 characters long
        while (ref.length() != 6
                || !Character.isLetter(ref.charAt(0))
                || !Character.isLetter(ref.charAt(1))
                || !Character.isDigit(ref.charAt(2))
                || !Character.isDigit(ref.charAt(3))
                || !Character.isDigit(ref.charAt(4))
                || !Character.isLetter(ref.charAt(5)))
        {
            
            if (ref.length() != 6)
            {
                System.out.println("Check that reference number is 6 characters long.");
            }
            else if (!Character.isLetter(ref.charAt(0)))
            {
                System.out.println("First character must be a letter.");
            }
            else if (!Character.isLetter(ref.charAt(1)))
            {
                System.out.println("Second character must be a letter.");
            }
            else if (!Character.isDigit(ref.charAt(2)))
            {
                System.out.println("Third character must be a digit.");
            }
            else if (!Character.isDigit(ref.charAt(3)))
            {
                System.out.println("Fourth character must be a digit.");
            }
            else if (!Character.isDigit(ref.charAt(4)))
            {
                System.out.println("Fifth character must be a digit");
            }
            else if (!Character.isLetter(ref.charAt(5)))
            {
                System.out.println("Sixth character must be a letter");
            }
            ref = input.next();
            
        }
        
        System.out.println("You have entered a valid reference number");
        return ref;

    }

    static int getNoOfGadgets()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter number of gadgets between 1 and 5 inclusive e.g. 5");
        int gadgets = input.nextInt();

        while (gadgets < 1 || gadgets > 5)
        {
            if (gadgets < 1)
            {
                System.out.println("You cannot enter less than one gadget");
            } 
            else if (gadgets > 5)
            {
                System.out.println("You cannot enter more than 5 gadgets");
            }

            gadgets = input.nextInt();
        }

        System.out.println("You have entered a valid number of gadgets.");
        return gadgets;
    }

    static int getGadgetValue()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter value of most expensive item in pence, e.g. for £500.59 enter 50059\n"
        + "Item cannot have a value of more than £1000.00 so please do not enter a number greater than 100000.");

        int value = input.nextInt();

        while (value <= 0 || value > 100000)
        {
            if (value < 1)
            {
                System.out.println("Item value cannot be negative or zero");
            } 
            else if (value > 100000)
            {
                System.out.println("Value cannot be over 100000 pence");
            }
            value = input.nextInt();
        }
        
        System.out.println("Valid value of most expensive item.");

        return value;
    }

    static int getBasicMonthlyPremium(int gadgets, int value)
    {
        int monthlyCover = 0;
        //calculate monthly premiums
        if (value <= 55000)
        {
            if (gadgets == 1)
            {
                monthlyCover = 499;
            }
            else if (gadgets >= 2 && gadgets < 4)
            {
                monthlyCover = 999;
            } 
            else if (gadgets >= 4 && gadgets < 6)
            {
                monthlyCover = 1499;
            }
        } 
        else if (value > 55000 && value <= 80000)
        {
            if (gadgets == 1)
            {
                monthlyCover = 615;
            } 
            else if (gadgets >= 2 && gadgets < 4)
            {
                monthlyCover = 1235;
            }
            else if (gadgets >= 4 && gadgets < 6)
            {
                monthlyCover = 1860;
            }
        }
        
        else if (value > 80000 && value <= 100000)
        {
            if (gadgets == 1)
            {
                monthlyCover = 730;
            } 
            else if (gadgets >= 2 && gadgets < 4)
            {
                monthlyCover = 1455;
            }
            else if (gadgets >= 4 && gadgets < 6)
            {
                monthlyCover = 2182;
            }
        }

        return monthlyCover;
    }
    
    static boolean getDefaultExcess()
    {
        boolean defaultExcess = false;
        Scanner input = new Scanner(System.in);
        System.out.println("The default excess is £30. Do you wish to increase the excess charge on making a claim "
                + "in order to pay a reduced premium? Enter YES or NO, or Y or N. Input is NOT case sensitive.");
        String answer = input.next();
        //make input uppercase
        answer = answer.toUpperCase();
        
        while (!answer.equals("Y") && !answer.equals("YES") && !answer.equals("N") && !answer.equals("NO"))
        {
            
            System.out.println("Enter a YES or NO, or Y or N. Input is NOT case sensitive.");
            answer = input.next();
            answer = answer.toUpperCase();
            
        }
       
        
        if(answer.equals("Y") || answer.equals("YES"))
            {
              defaultExcess = false;
            }
            else if(answer.equals("N") || answer.equals("NO"))
            {
              defaultExcess = true;
            }
            
            return defaultExcess;
    }
    
    static double getExcessValidation(boolean defaultExcess)
    {
        
        int option = 30;
        
        if(defaultExcess == true)
        {
            option = 30;
        }
        else if(defaultExcess == false)
        {
            do
            {
                Scanner input = new Scanner(System.in);
                System.out.println("Enter a increased excess charge percentage in multiples of 10"
                    + " between 40 and 70 inclusive.");
                
                option = input.nextInt();
                              
            }while(option < 40 || option > 70 || option % 10 != 0);
            System.out.println("You have entered a valid selection.");
        }
        return option;
    }
    
    static double getNewMonthlyPremium(double excessValidation, double basicMonthlyPremium, boolean defaultExcess)
    {
        //declare discount
        double discount;
        //declare and initialise discounted premium with a value to be changed by if statements
        double newMonthlyPremium = 0;
        
        //premium not reduced
        if(defaultExcess == true)
        {
            newMonthlyPremium = basicMonthlyPremium;
            //excessValidation = 30;
        }
        
        else if(excessValidation == 40)
        {
            //get discount amount
            discount = basicMonthlyPremium * 0.05;
            //subtract discount from monthly premium to get the discounted monthly premium
            newMonthlyPremium = basicMonthlyPremium - discount;
        }
        else if(excessValidation == 50)
        {
            discount = basicMonthlyPremium * 0.1;
            newMonthlyPremium = basicMonthlyPremium - discount;
        }
        else if(excessValidation == 60)
        {

            discount = basicMonthlyPremium * 0.15;
            newMonthlyPremium = basicMonthlyPremium - discount;
            
        }
        else if(excessValidation == 70)
        {

            discount = basicMonthlyPremium * 0.2;
            newMonthlyPremium = basicMonthlyPremium - discount;
            
        }
        return newMonthlyPremium;
    }
    
    static double getBasicAnnualPremium(double newMonthlyPremium)
    {
        //declare annualCover variable
        double annualCover;
        
        //multiply monthlyPremium by 12 to get annual cover 
        annualCover = newMonthlyPremium * 12;
        
        //return annualCover to the getBasicAnnualPremium method
        return annualCover;
    }
    
    static boolean getAnnualPaymentOption()
    {
        boolean annualOption = false;
                
        Scanner input = new Scanner(System.in);
        //prompt user
        System.out.println("Do you want to pay annually? This will give you a 10% discount. Enter YES or NO, or Y or N. "
        + "input is NOT case sensitive.");
        String answer = input.next();
        //change user input to uppercase        
        answer = answer.toUpperCase();        
        
        //validate user input
        while(!answer.equals("Y") && !answer.equals("YES") && !answer.equals("N") && !answer.equals("NO"))
        {
            System.out.println("Enter a YES or NO, or Y or N. Input is NOT case sensitive.");
            answer = input.next();
            answer = answer.toUpperCase(); 
        }
        
        if(answer.equals("Y") || answer.equals("YES"))
            {
              annualOption = true;
            }
            else if(answer.equals("N") || answer.equals("NO"))
            {
              annualOption = false;
            }
            
        return annualOption;
    }
    
    static char getPaymentTerms(boolean annualPaymentOption)
    {
        char term;
        
        if(annualPaymentOption == true)
        {
            term = 'A';
        }
        else
        {
            term = 'M';
        }
        
        return term;
    }
    static double getNewAnnualPremium(boolean annualCoverOption, double getBasicAnnualPremium)
    {
        //declare and initialise variables if required
        double discount;
        double newAnnualPremium = 0;
        
        //if an annual policy is selected then calculate and apply discount
        if(annualCoverOption)
        {
            discount = getBasicAnnualPremium * 0.1;
            newAnnualPremium = getBasicAnnualPremium - discount;
        }
        
        return newAnnualPremium;
    }
    
    static double getPremium(boolean annualCoverOption, double newMonthlyPremium, double newAnnualPremium)
    {
        //declare premium
        double premium;
         
        //if an annual policy is selected premium is set to discounted annual premium
        if(annualCoverOption)
        {
            premium = newAnnualPremium / 100;
        }
        //otherwise premium is set to the monthly premium which may or may not have been discounted
        else
        {
            premium = newMonthlyPremium / 100;
        }
   
        return premium;
    }
    
    static int getPremiumPence(double premium)
    {
        double pence = premium * 100;
        int penceB = (int)pence;
        return penceB;
    }
    
    static String getSurname(String clientName)
    {
        //get position of last space in client's name
        int lastSpace = clientName.lastIndexOf(" ");
        
        //assign substring to newly created surname string
        String surname = clientName.substring(lastSpace);
        
        return surname;
    }
    
    static String getFirstInitial(String clientName)
    {
        //get first character in clientName using the substring method
        String firstInitial = clientName.substring(0, 1);
     
        //Change first character in first and make it uppercase
        firstInitial = firstInitial.toUpperCase();
        
        return firstInitial;
    }
    
    static String getAbbrevClientName(String firstInitial, String surname)
    {
        //concatenate first initial and surname and assign it to newly created abbrevName
        String abbrevName = firstInitial + surname;
        
        return abbrevName;
    }
    
    static String getDate()
    {
        //get date
        Calendar date = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
       
        return sdf.format(date.getTime());    
    }
    
    static String getDisplayCover (boolean annualCoverOption)
    {
       
        String displayCover = new String();
        
        if(annualCoverOption)
        {
            displayCover = ("Annual");
        }
        else
        {
            displayCover = ("Monthly");
        }
        
        displayCover = (displayCover);
        
        return displayCover;
    }
    
    static String getDateFormat(String date)
    {
        //create date format from getDate method and return it as a string
        String dateFormat = getDate();
        
        dateFormat = (dateFormat);
        
        return dateFormat;
    }
    
    static String getUppercaseRef(String refNo)
    {
        //format reference number
        String displayRefNo = refNo;

        displayRefNo = displayRefNo.toUpperCase();
        
        return displayRefNo;
    }
    
    static String getDisplayItems(int noOfGadgets)
    {
        String gadgetTxt = new String();
        
        switch (noOfGadgets)
        {
            case 1:
                gadgetTxt = ("One");
                break;
            case 2:
                gadgetTxt = ("Two");
                break;
            case 3:
                gadgetTxt = ("Three");
                break;
            case 4:
                gadgetTxt = ("Four");
                break;
            case 5:
                gadgetTxt = ("Five");
                break;
            default:
                break;
        }
        
        return gadgetTxt;
    }
   
    static void getSummaryMethod(String abbrevClientName, String dateFormat,
            String refNo, String displayCover, String displayItems, double excessValidation, double premium, int gadgetValue)
    {


	//display summary with formatting
        String border = new String("+==========================================+");
        String emptyBox = new String("|                                          |");
        System.out.println(border);
        System.out.println(emptyBox);
        System.out.printf("|  Client: %-25s", abbrevClientName + "                   |\n");
        System.out.println(emptyBox);
        System.out.printf("|   Date: %-1s    Ref: %6s", dateFormat, refNo + "       |\n");
        System.out.printf("|  Terms: %-3s       Items: %5s", displayCover, displayItems + "        |\n");
        System.out.printf("| Excess:  £%.2f                          |\n", excessValidation );    
        System.out.println(emptyBox);
        System.out.printf("|  %-7s", displayCover + "               Limit per          |\n" );
        System.out.printf("|  Premium: £%.2f       Gadget: %4s", premium, gadgetValue + "    |\n");
        System.out.println(emptyBox);
        System.out.println(border);             
    }
}
 

