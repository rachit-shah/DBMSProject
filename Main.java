import java.util.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    DBManager dbManager = new DBManager();
    try{
      dbManager.init(); //Create database and tables
      dbManager.insertSampleData();
    }
    catch(Exception e){
      System.out.println("Error Creating Database");
      System.exit(0);
    }
    System.out.println("Welcome to WolfDB! Log in to your account.");
    System.out.println("Enter Username:");
    String userName = sc.nextLine();
    System.out.println("Enter Password");
    String password = sc.nextLine();
    String userType = "";
    try{
      userType = findUserClass(dbManager,userName,password);
    }
    catch(Exception e){
      System.out.println("Error finding userclass");
      System.exit(0);
    }
    System.out.println(userType);
    System.out.println("Determine what you want to do:");
    System.out.println("1. Editing and Publishing");
    System.out.println("2. Production");
    System.out.println("3. Distribution");
    System.out.println("4. Reports");
    int choice = sc.nextInt();
    switch(choice){
      case 1: editingAndPublishing(); break;
      case 2: production(); break;
      case 3: distribution(dbManager,sc); break;
      case 4: reports(); break;
      default: System.out.println("Invalid Choice");
      break;
    }
    dbManager.closeConnection();
  }
  public static void editingAndPublishing(){
    //switch case operations
    int flag = 1;
    while(flag == 1)
    {
    System.out.println("Determine what Editing and Publishing activity you want to perfrom:");
    System.out.println("1. Add Publication");
    System.out.println("2. Update Publication");
    System.out.println("3. Delete Publication");
    System.out.println("4. Add Article");
    System.out.println("5. Update Article");
    System.out.println("6. Delete Article");
    System.out.println("7. Add Chapter");
    System.out.println("8. Update Chapter");
    System.out.println("9. Delete Chapter");
    System.out.println("10. Add editor to publication");
    System.out.println("11. Delete editor from publication");
    System.out.println("12. View assigned publication");
    System.out.println("13. View tableofcontent – issue of magazine for a publication");
    System.out.println("14.View tableofcontent – issue of journal for a publication");
    System.out.println("15.View tableofcontent – edition of book for a publication");
    System.out.println("16.Update tableofcontent – issue of magazine for a publication");
    System.out.println("17.Update tableofcontent – issue of journal for a publication");
    System.out.println("18.Update tableofcontent – edition of book for a publication");
    int chosenval = sc.nextInt();
    switch(chosenval){

      //enter publication
      case 1: 
      System.out.println("Enter the publication ID of the publication to insert");
      int pubId = sc.nextInt();
      System.out.println("Enter the title of the publication to insert");
      String title = sc.nextLine();
      String query = "INSERT INTO Publication(pubID,title) VALUES("+pubId+","+title+");";
      dbManager.executeUpdate(query);
      break;

      //edit publication 
      case 2:
      System.out.println("Enter the publication ID of the publication to update");
      pubId = sc.nextInt();
      System.out.println("Enter the title of the publication to update");
      title = sc.nextLine();
      query = "UPDATE Publication SET title =" + title + "WHERE pubID=" + pubId +";";
      dbManager.executeUpdate(query);
      break;

      //delete publication
      case 3:
      System.out.println("Enter the publication ID of the publication to delete");
      int pubId = sc.nextInt();
      query = "DELETE FROM Publication WHERE pubID ="+pubID +";";
      dbManager.executeUpdate(query);
      break;



      //inserting article
      case 4:
      String title = Types.NULL;
      String date = Types.NULL;
      String text = Types.NULL;
      System.out.println("Enter the article ID of the article to insert");
      int artId = sc.nextInt();
      System.out.println("Enter the topic of the article to insert");
      String topic = sc.nextLine();
      System.out.println("Do you want to enter the title of the article? Enter 1 for Yes or 0 for No");
      int check = sc.nextInt();
      if(check == 1)
      {
        System.out.println("Enter the title of the article to insert");
        title = sc.nextLine();
      }
      System.out.println("Do you want to enter the date of the article? Enter 1 for Yes or 0 for No");
      int check1 = sc.nextInt();
      if(check1 == 1)
      {
        System.out.println("Enter the date of the article to insert");
        date = sc.nextLine();
      }
      System.out.println("Do you want to enter the text of the article? Enter 1 for Yes or 0 for No");
      int check2 = sc.nextInt();
      if(check2 == 1)
      {
        System.out.println("Enter the text of the article to insert");
        text = sc.nextLine();
      }
      query = "INSERT INTO Article(artID, topic, title, date,text) VALUES("+artId+","+topic+","+title+","+date+","+text+");";
      dbManager.executeUpdate(query);
      break;



      //updating article
      case 5:
    System.out.println("Enter the article id you want to update");
    artId = sc.nextInt();  
    int chosenval1 = 1;
    while(chosenval1 == 1)
    {
      System.out.println("Determine what you want to do in updating article");
      System.out.println("1. update topic");
      System.out.println("2. update title");
      System.out.println("3. update date");
      System.out.println("4. update text");
      int choice1 = sc.nextChar();
      switch(choice1)
      {
      case 1:
        System.out.println("Enter the topic of the article to update");
        topic = sc.nextLine();
        query = "UPDATE Article SET topic ="+topic+" WHERE artID="+artId+";";
        dbManager.executeUpdate(query);
      break;
      case 2:
        System.out.println("Enter the title of the article to update");
        title = sc.nextLine();
        query = "UPDATE Article SET title ="+title+" WHERE artID="+artId+";";
        dbManager.executeUpdate(query);  
      break;
      case 3:
        System.out.println("Enter the date of the article to update");
        date = sc.nextLine();
        query = "UPDATE Article SET date ="+date+" WHERE artID="+artId+";";
        dbManager.executeUpdate(query);  
      break;
      case 4:
        System.out.println("Enter the text of the article to update");
        text = sc.nextLine();
        query = "UPDATE Article SET text ="+text+" WHERE artID="+artId+";";
        dbManager.executeUpdate(query);  
      break;
      default:
      System.out.println("invalid option entered");
      break;
      }
      System.out.println("Do you want to update other fields of article 1 for yes 0 for no");
      chosenval1 = sc.nextInt();
    }
      break;



      //delete an article
      case 6:
      System.out.println("Enter the article id you want to delete");
      artId = sc.nextInt();
      query = "DELETE FROM Article WHERE artID="+artId+";";
      dbManager.executeUpdate(query);    
      break;


      //insert chapter
      case 7:
      date = Types.NULL;
      text = Types.NULL;
      System.out.println("Enter the chapter ID of the chapter to insert");
      int chapId = sc.nextInt();
      System.out.println("Enter the title of the chapter to insert");
      String title = sc.nextLine();
      System.out.println("Do you want to enter the date of the chapter? Enter 1 for Yes or 0 for No");
      int check3 = sc.nextInt();
      if(check3 == 1)
      {
        System.out.println("Enter the date of the chapter to insert");
        date = sc.nextLine();
      }
      System.out.println("Do you want to enter the text of the chapter? Enter 1 for Yes or 0 for No");
      int check4 = sc.nextInt();
      if(check4 == 1)
      {
        System.out.println("Enter the text of the chapter to insert");
        text = sc.nextLine();
      }
      query = "INSERT INTO Chapter(chapID, title, date, text) VALUES("+chapId+","+title+","+date+","+text+");";
      dbManager.executeUpdate(query);
      break;


      //update chapter
      case 8:
      System.out.println("Enter the chapter id you want to update");
    int chapid = sc.nextInt();  
    int chosenval5 = 1;
    while(chosenval5 == 1)
    {
    System.out.println("Determine what you want to do in updating chapter");
    System.out.println("1. update title");
    System.out.println("2. update date");
    System.out.println("3. update text");
    int choice1 = sc.nextChar();
    switch(choice1)
    {
    case 1:
      System.out.println("Enter the title of the chapter to update");
      title = sc.nextLine();
      query = "UPDATE Chapter SET title ="+title+" WHERE chapID="+chapid+";";
      dbManager.executeUpdate(query);  
    break;
    case 2:
        System.out.println("Enter the date of the chapter to update");
        date = sc.nextLine();
        query = "UPDATE Article SET date ="+date+" WHERE chapID="+chapid+";";
        dbManager.executeUpdate(query);  
    break;
    case 3:
        System.out.println("Enter the text of the chapter to update");
        text = sc.nextLine();
        query = "UPDATE Article SET text ="+text+" WHERE chapID="+chapid+";";
        dbManager.executeUpdate(query);  
    break;
    default:
    System.out.println("invalid option entered");
    break;
    }
      System.out.println("Do you want to update other fields of chapter 1 for yes 0 for no");
      chosenval5 = sc.nextInt();
    }

      break;





      //delete chapter
      case 9:
      System.out.println("Enter the chapter id you want to delete");
      chapId = sc.nextInt();
      query = "DELETE FROM Chapter WHERE chapID="+chapid+";";
      dbManager.executeUpdate(query);    
      break;

      break;


      //assigning editor to publication
      case 10:
      System.out.println("Enter the editor staff id");
      int editorId = sc.nextInt();
      System.out.println("Enter the publication id to which the editor is to be assigned");
      pubId = sc.nextInt();
      query = "INSERT INTO EditorAssigned(staffID, pubID) VALUES("+editorId+","+pubId+");";
      dbManager.executeUpdate(query);

      break;




      //deleting editor from publication
      case 11:
      System.out.println("Enter the editor staff id");
      editorId = sc.nextInt();
      System.out.println("Enter the publication id to which the editor is to be removed from");
      pubId = sc.nextInt();
      query = "DELETE FROM EditorAssigned WHERE pubId="+pubId+
" and staffID="+editorId+";";
      dbManager.executeUpdate(query);

      break;

      //view assigned publication
      case 12:
      System.out.println("Enter the staff id to view the assigned publications");
      int id = sc.nextInt();
       query = "SELECT title FROM Publication WHERE pubId= ( SELECT pubId FROM EditorAssigned WHERE staffID="+id+");";
      dbManager.executeUpdate(query);


      break;



      //View tableofcontent – issue of magazine for a publication
      case 13:
      System.out.println("Enter the publication id");
      pubId = sc.nextInt();
      System.out.println("Enter the issue id of publication");
      int issueId = sc.nextInt();
      query = "SELECT tabOfCont FROM Issue WHERE pubID="+pubId+" and issueID="+issueId+";";
      dbManager.executeUpdate(query);
      break;


       //View tableofcontent – issue of magazine for a publication
      case 14:
      System.out.println("Enter the publication id");
      int pubId = sc.nextInt();
      System.out.println("Enter the issue id of publication");
      int issueId = sc.nextInt();
      query = "SELECT tabOfCont FROM Issue WHERE pubID="+pubId+" and issueID="+issueId+";";
      dbManager.executeUpdate(query);



      break;



      //View tableofcontent – edition of book for a publication :
      case 15:
      System.out.println("Enter the publication id");
      int pubId = sc.nextInt();
      System.out.println("Enter the edition id of publication");
      int editionId = sc.nextInt();
      query = "SELECT tabOfCont FROM Edition WHERE pubID="+pubId+" and editionID="+editionId+";";
      dbManager.executeUpdate(query);

      break;



      //Update tableofcontent – issue of magazine for a publication :
      case 16:

      System.out.println("Enter the publication id");
      int pubId = sc.nextInt();
      System.out.println("Enter the issue id of publication");
      int issueId = sc.nextInt();
      System.out.println("Enter the table of content details");
      String tabofCont = sc.nextLine();
      query = "UPDATE Issue SET tabOfCont="+tabofCont+" WHERE pubID="+pubId+" and issueID="+issueId+";";
      dbManager.executeUpdate(query);

      break;



//Update tableofcontent – issue of journal for a publication :
      case 17:
      System.out.println("Enter the publication id");
      int pubId = sc.nextInt();
      System.out.println("Enter the issue id of publication");
      int issueId = sc.nextInt();
      System.out.println("Enter the table of content details");
      String tabofCont = sc.nextLine();
      query = "UPDATE Issue SET tabOfCont="+tabofCont+" WHERE pubID="+pubId+" and issueID="+issueId+";";
      dbManager.executeUpdate(query);

      break;


//Update tableofcontent – edition of book for a publication :
      case 18:
      System.out.println("Enter the publication id");
      int pubId = sc.nextInt();
      System.out.println("Enter the edition id of publication");
      int editionid = sc.nextInt();
      System.out.println("Enter the table of content details");
      String tabofCont = sc.nextLine();
      query = "UPDATE Edition SET tabOfCont="+tabofCont+" WHERE pubID="+pubId+" and editionID="+editionid+";";
      dbManager.executeUpdate(query);


      break;


      default: System.out.println("Invalid Choice");
      break;
    }
    System.out.println("Would you like to perfrom another editing of publishing task? enter 1 for Yes or 0 for N");
    flag = sc.nextInt();
    }



  }
  public static void production(){
    //switch case operations
  }

  public static void distribution(DBManager dbManager, Scanner sc){
    //switch case operations
    boolean flag = true;
    while(flag){
      System.out.println("-------------------------------------------------------------------------------------------");
      System.out.println("Determine what Distribution activity you want to perfrom:");
      System.out.println("1. Enter New Distributor Info to DB");
      System.out.println("2. Update Distributor Info in DB");
      System.out.println("3. Delete a Distributor from DB");
      System.out.println("4. Enter Distributor and Order Info for an Issue of Journal or Magazine for a certain date");
      System.out.println("5. Enter Distributor and Order Info for an Edition of Book for a certain date");
      System.out.println("6. Generate Bill for a Distributor for a particular order");
      System.out.println("7. Change outstanding balance of a distributor on receipt of a payment");
      System.out.println("8. Search for a distributor");
      System.out.println("-------------------------------------------------------------------------------------------");
      int choice = sc.nextInt();
      String query = "";
      int distID,ordID,numCopies,pubID,issueID,editionID,payID;
      String distName,contactPerson,distType,distStAddr,distCity,distPhone,orderDate,deliveryDate;
      double distBal,priceCopy,shipCost;
      switch(choice){
        case 1:
        System.out.println("----------------------------------------");
        System.out.println("==> Enter New Distributor Info to DB <==");
        System.out.println("----------------------------------------");
        System.out.println("Fill out the following details for the new distributor:");
        System.out.println("Enter Distributor ID for the new distributor:");
        distID = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter name for the new distributor:");
        distName = sc.nextLine();
        System.out.println("Enter the name of a contact person:");
        contactPerson = sc.nextLine();
        System.out.println("Enter Type of the new distributor:");
        distType = sc.nextLine();
        System.out.println("Enter Street Address for the new distributor:");
        distStAddr = sc.nextLine();
        System.out.println("Enter City of the new ditributor:");
        distCity = sc.nextLine();
        System.out.println("Enter Phone Number of the new distributor:");
        distPhone = sc.nextLine();
        System.out.println("Enter outstanding balance for the new distributor:");
        distBal = sc.nextDouble();
        sc.nextLine();

        query = "INSERT INTO Distributors (distID, name, contactPerson, type, strAddr, city, phone, balance) "+
                "VALUES (%d,\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",%f)";
        query = String.format(query, distID, distName, contactPerson, distType, distStAddr, distCity, distPhone, distBal);
        try{
          dbManager.executeUpdate(query);
          System.out.println("~~~ Successfully added New Distributor to DB ~~~");
        }
        catch(Exception e){
          e.printStackTrace();
          System.out.println("!!! Error Adding New Distributor to DB !!!");
        }
        break;

        case 2:
        System.out.println("-------------------------------------");
        System.out.println("==> Update Distributor Info in DB <==");
        System.out.println("-------------------------------------");
        System.out.println("Enter Distributor ID of distributor: ");
        distID = sc.nextInt();
        sc.nextLine();
        System.out.println("Which parameter do you want to update for distributor with ID "+distID);
        System.out.println("1. Distributor ID");
        System.out.println("2. Distributor Name");
        System.out.println("3. Distributor Contact Person");
        System.out.println("4. Distributor Type");
        System.out.println("5. Distributor Street Address");
        System.out.println("6. Distributor City");
        System.out.println("7. Distributor Phone");
        System.out.println("8. Distributor Balance");
        int choicen = sc.nextInt();
        sc.nextLine();
        switch(choicen){
          case 1:
          System.out.println("Enter updated Distributor ID: ");
          int newID = sc.nextInt();
          sc.nextLine();
          query = "UPDATE Distributors SET distID="+newID+" WHERE distID="+distID;
          break;

          case 2:
          System.out.println("Enter updated Distributor Name: ");
          String newName = sc.nextLine();
          query = "UPDATE Distributors SET name=\""+newName+"\" WHERE distID="+distID;
          break;

          case 3:
          System.out.println("Enter updated Distributor Contact Person: ");
          String newPOC = sc.nextLine();
          query = "UPDATE Distributors SET contactPerson=\""+newPOC+"\" WHERE distID="+distID;
          break;

          case 4:
          System.out.println("Enter updated Distributor Type: ");
          String newType = sc.nextLine();
          query = "UPDATE Distributors SET type=\""+newType+"\" WHERE distID="+distID;
          break;

          case 5:
          System.out.println("Enter updated Distributor Street Address: ");
          String newAddr = sc.nextLine();
          query = "UPDATE Distributors SET strAddr=\""+newAddr+"\" WHERE distID="+distID;
          break;

          case 6:
          System.out.println("Enter updated Distributor City: ");
          String newCity = sc.nextLine();
          query = "UPDATE Distributors SET city=\""+newCity+"\" WHERE distID="+distID;
          break;

          case 7:
          System.out.println("Enter updated Distributor Phone: ");
          String newPhone = sc.nextLine();
          if(newPhone.length() != 10){
            System.out.println("Phone number should be of 10 digits");
            break;
          }
          query = "UPDATE Distributors SET phone=\""+newPhone+"\" WHERE distID="+distID;
          break;

          case 8:
          System.out.println("Enter updated Distributor Balance: ");
          double newBal = sc.nextDouble();
          sc.nextLine();
          query = "UPDATE Distributors SET balance="+newBal+" WHERE distID="+distID;
          break;
          default: 
          System.out.println("Invalid Choice");
          query = null;
          break;
        }
        if(query != null){
          try{
            dbManager.executeUpdate(query);
            System.out.println("~~~ Successfully updated record ~~~");
          }
          catch(Exception e){
            e.printStackTrace();
            System.out.println("!!! Error Updating !!!");
          }
        }
        break;

        case 3:
        System.out.println("------------------------------------");
        System.out.println("==> Delete a Distributor from DB <==");
        System.out.println("------------------------------------");
        System.out.println("Enter Distributor ID of distributor: ");
        distID = sc.nextInt();
        sc.nextLine();
        query = "DELETE FROM Distributors WHERE distID="+distID;
        try{
          dbManager.executeUpdate(query);
          System.out.println("~~~ Successfully deleted record ~~~");
        }
        catch(Exception e){
          e.printStackTrace();
          System.out.println("!!! Error Deleting !!!");
        }
        break;

        case 4:
        System.out.println("---------------------------------------------------");
        System.out.println("==> Enter Distributor & Order Info for an Issue <==");
        System.out.println("==>  of Journal or Magazine for a certain date  <==");
        System.out.println("---------------------------------------------------");

        System.out.println("--Identify Publication for the Order--");
        System.out.println("Enter Publication ID of the Journal or Magazine:");
        pubID = sc.nextInt();
        System.out.println("Enter Issue ID of the Issue of the Journal or Magazine for the Order:");
        issueID = sc.nextInt();
        //check whether the issue and publication exists
        query = "SELECT * FROM Issue WHERE issueID="+issueID+" AND pubID="+pubID;
        try{
          ResultSet rs = dbManager.executeQuery(query);
          if(!rs.next()){
            System.out.println("The provided issueID and/or pubID doesn't exist in the DB");
            break;
          }
        }
        catch(Exception e){
          e.printStackTrace();
          System.out.println("Error searching issueID and pubID in DB");
          break;
        }

        System.out.println("--Identify Distributor for the Order--");
        System.out.println("Enter Distributor ID for the Order:");
        distID = sc.nextInt();
        //check whether the distributor exists in the DB
        query = "SELECT * FROM Distributors WHERE distID="+distID;
        try{
          ResultSet rs = dbManager.executeQuery(query);
          if(!rs.next()){
            System.out.println("The provided distID doesn't exist in the DB");
            break;
          }
        }
        catch(Exception e){
          e.printStackTrace();
          System.out.println("Error searching distID in DB");
          break;
        }

        System.out.println("--Enter Order Information--");
        System.out.println("Enter Order ID for the new order: ");
        ordID = sc.nextInt();
        System.out.println("Enter Number of Copies for the Order: ");
        numCopies = sc.nextInt();
        System.out.println("Enter Price of Each Copy: ");
        priceCopy = sc.nextDouble();
        System.out.println("Enter Shipping Cost of the Order: ");
        shipCost = sc.nextDouble();
        sc.nextLine();
        System.out.println("Enter Order Date [YYYY-MM-DD] (leave blank if today's date)");
        orderDate = sc.nextLine();
        if(!checkValidDate(orderDate)){
          if(orderDate.equals("")){
            orderDate = LocalDate.now().toString();
          }
          else{
            System.out.println("Invalid Date Format");
          }
        }
        System.out.println("Enter Delivery Date [YYYY-MM-DD] (leave blank if today's date)");
        deliveryDate = sc.nextLine();
        if(!checkValidDate(deliveryDate)){
          if(deliveryDate.equals("")){
            deliveryDate = LocalDate.now().toString();
          }
          else{
            System.out.println("Invalid Date Format");
          }
        }

        //Enter information to DB with transactions to maintain atomicity
        try{
          dbManager.startTransaction();
          //Step 1: Add Order Information
          query = "INSERT INTO Orders (orderID, orderDate, deliveryDate, price, numCopies, shipCost) VALUES (%d,\"%s\",\"%s\",%f,%d,%f)";
          query = String.format(query,ordID,orderDate,deliveryDate,priceCopy,numCopies,shipCost);
          dbManager.executeUpdate(query);

          //Step 2: Assign the order to the distributor
          query = "INSERT INTO DistAssigned (distID, orderID) VALUES (%d,%d)";
          query = String.format(query, distID, ordID);
          dbManager.executeUpdate(query);

          //Step 3: Associate the order with the issue of publication
          query = "INSERT INTO OrderHasIssue(orderID, issueID, pubID) VALUES (%d,%d,%d)";
          query = String.format(query, ordID, issueID, pubID);
          dbManager.executeUpdate(query);

          //If all updates run correctly, commit
          dbManager.commitTransaction();
          System.out.println("~~~ Order Created Successfully! ~~~");
        }
        catch(Exception e){
          try{
            dbManager.rollbackTransaction();
          }
          catch(Exception ex){
            System.out.println("!!! Error rolling back transaction. Connection to DB Lost. !!!");
          }
          System.out.println("!!! Error in transactions. Rolling back. !!!");
        }
        break;

        case 5:
        System.out.println("-----------------------------------------------");
        System.out.println("==>  Enter Distributor and Order Info for   <==");
        System.out.println("==>  an Edition of Book for a certain date  <==");
        System.out.println("-----------------------------------------------");

        System.out.println("--Identify Publication for the Order--");
        System.out.println("Enter Publication ID of the Book:");
        pubID = sc.nextInt();
        System.out.println("Enter Edition ID of the Book for the Order:");
        editionID = sc.nextInt();
        //check whether the edition and publication exists
        query = "SELECT * FROM Edition WHERE editionID="+editionID+" AND pubID="+pubID;
        try{
          ResultSet rs = dbManager.executeQuery(query);
          if(!rs.next()){
            System.out.println("The provided editionID and/or pubID doesn't exist in the DB");
            break;
          }
        }
        catch(Exception e){
          e.printStackTrace();
          System.out.println("Error searching editionID and pubID in DB");
          break;
        }

        System.out.println("--Identify Distributor for the Order--");
        System.out.println("Enter Distributor ID for the Order:");
        distID = sc.nextInt();
        //check whether the distributor exists in the DB
        query = "SELECT * FROM Distributors WHERE distID="+distID;
        try{
          ResultSet rs = dbManager.executeQuery(query);
          if(!rs.next()){
            System.out.println("The provided distID doesn't exist in the DB");
            break;
          }
        }
        catch(Exception e){
          e.printStackTrace();
          System.out.println("Error searching distID in DB");
          break;
        }

        System.out.println("--Enter Order Information--");
        System.out.println("Enter Order ID for the new order: ");
        ordID = sc.nextInt();
        System.out.println("Enter Number of Copies for the Order: ");
        numCopies = sc.nextInt();
        System.out.println("Enter Price of Each Copy: ");
        priceCopy = sc.nextDouble();
        System.out.println("Enter Shipping Cost of the Order: ");
        shipCost = sc.nextDouble();
        sc.nextLine();
        System.out.println("Enter Order Date [YYYY-MM-DD] (leave blank if today's date)");
        orderDate = sc.nextLine();
        if(!checkValidDate(orderDate)){
          if(orderDate.equals("")){
            orderDate = LocalDate.now().toString();
          }
          else{
            System.out.println("Invalid Date Format");
          }
        }
        System.out.println("Enter Delivery Date [YYYY-MM-DD] (leave blank if today's date)");
        deliveryDate = sc.nextLine();
        if(!checkValidDate(deliveryDate)){
          if(deliveryDate.equals("")){
            deliveryDate = LocalDate.now().toString();
          }
          else{
            System.out.println("Invalid Date Format");
          }
        }

        //Enter information to DB with transactions to maintain atomicity
        try{
          //start transaction (set auto-commit false)
          dbManager.startTransaction();
          //Step 1: Add Order Information
          query = "INSERT INTO Orders (orderID, orderDate, deliveryDate, price, numCopies, shipCost) VALUES (%d,\"%s\",\"%s\",%f,%d,%f)";
          query = String.format(query,ordID,orderDate,deliveryDate,priceCopy,numCopies,shipCost);
          dbManager.executeUpdate(query);

          //Step 2: Assign the order to the distributor
          query = "INSERT INTO DistAssigned (distID, orderID) VALUES (%d,%d)";
          query = String.format(query, distID, ordID);
          dbManager.executeUpdate(query);

          //Step 3: Associate the order with the issue of publication
          query = "INSERT INTO OrderHasEdition (orderID, editionID, pubID) VALUES (%d,%d,%d)";
          query = String.format(query, ordID, editionID, pubID);
          dbManager.executeUpdate(query);

          //If all updates run correctly, commit and set auto-commit back to true
          dbManager.commitTransaction();
          System.out.println("~~~ Order Created Successfully! ~~~");
        }
        catch(Exception e){
          //If anything fails, rollback and set auto-commit back to true
          try{
            dbManager.rollbackTransaction();
          }
          catch(Exception ex){
            System.out.println("!!! Error rolling back transaction. Connection to DB Lost. !!!");
          }
          System.out.println("!!! Error in transactions. Rolling back. !!!");
          break;
        }
        break;

        case 6:
        System.out.println("--------------------------------------------------------------");
        System.out.println("==> Generate Bill for a Distributor for a particular order <==");
        System.out.println("--------------------------------------------------------------");

        System.out.println("--Identify Order--");
        System.out.println("Enter Order ID of the Order:");
        ordID = sc.nextInt();
        //check whether the order exists
        query = "SELECT * FROM Orders WHERE orderID="+ordID;
        try{
          ResultSet rs = dbManager.executeQuery(query);
          if(!rs.next()){
            System.out.println("The provided order ID doesn't exist in the DB");
            break;
          }
          priceCopy = rs.getDouble("price");
          numCopies = rs.getInt("numCopies");
          shipCost = rs.getDouble("shipCost");
        }
        catch(Exception e){
          e.printStackTrace();
          System.out.println("Error searching order ID in DB");
          break;
        }
        //Find Publication Info
        String pubType;
        String pubTitle;
        issueID=-1;
        editionID=-1;
        try{
          query = "select orderID,pubID,title,issueID from Orders NATURAL JOIN OrderHasIssue NATURAL JOIN Publication WHERE orderID="+ordID;
          ResultSet rs = dbManager.executeQuery(query);
          if(rs.next()){
            pubID = rs.getInt("pubID");
            pubTitle = rs.getString("title");
            pubType = "Issue";
            issueID = rs.getInt("issueID");
          }
          else{
            try{
              query = "select orderID,pubID,title,editionID from Orders NATURAL JOIN OrderHasEdition NATURAL JOIN Publication WHERE orderID="+ordID;
              rs = dbManager.executeQuery(query);
              if(rs.next()){
                pubID = rs.getInt("pubID");
                pubTitle = rs.getString("title");
                pubType = "Edition";
                editionID = rs.getInt("editionID");
              }
              else{
                System.out.println("Couldn't locate publication for given order");
                break;
              }
            }
            catch(Exception e){
              e.printStackTrace();
              System.out.println("Couldn't locate publication for given order");
              break;
            }
          }
        }
        catch(Exception e){
          e.printStackTrace();
          System.out.println("Couldn't locate publication for given order");
          break;
        }

        //Find Distributor
        query = "SELECT distID,name,city FROM DistAssigned NATURAL JOIN Distributors WHERE orderID="+ordID;
        try{
          ResultSet rs = dbManager.executeQuery(query);
          if(!rs.next()){
            System.out.println("The provided order ID doesn't exist in the DB");
            break;
          }
          distID = rs.getInt("distID");
          distName = rs.getString("name");
          distCity = rs.getString("city");
        }
        catch(Exception e){
          e.printStackTrace();
          System.out.println("Error searching order ID in DB");
          break;
        }
        System.out.println("The provided order ID corresponds to distributor "+distName+" (ID:"+distID+") from "+distCity+" city.");
        System.out.println("Do you want to bill this distributor for the order? 1 for Yes or 0 for No");
        int choiced = sc.nextInt();
        sc.nextLine();
        if( choiced != 1){
          break;
        }

        //Generate Bill
        double billAmt = (priceCopy*numCopies)+shipCost;
        try{
          query = "UPDATE Distributors SET balance=balance+"+billAmt+" WHERE distID="+distID;
          dbManager.executeUpdate(query);
        }
        catch(Exception e){
          e.printStackTrace();
          System.out.println("Error updating balance of distributor");
          break;
        }
        System.out.println("~~~ Successfully updated outstanding balance of distributor. Save the bill printed below! ~~~");
        System.out.println("-------------------------BILL-------------------------------");
        System.out.println("Order ID: "+ordID+"                       Bill Date: "+LocalDate.now().toString());
        System.out.println("Publication: "+pubTitle+" (Pub ID: "+pubID+")");
        if(pubType=="Issue"){
          System.out.println("Issue ID: "+issueID);
        }
        else{
          System.out.println("Edition ID: "+editionID);
        }
        System.out.println("Distributor: "+distName+" (Dist ID: "+distID+") City: "+distCity);
        System.out.println("");
        System.out.println("Number of Copies: "+numCopies+"  x Price Per Copy: "+priceCopy);
        System.out.println("Shipping Cost: "+shipCost);
        System.out.println("------------------------------------------------------------");
        System.out.println("Total Amount Owed: "+billAmt);
        System.out.println("------------------------------------------------------------");
        break;

        case 7:
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("==> Change outstanding balance of a distributor on receipt of a payment <==");
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("Enter Order ID connected to payment: ");
        ordID = sc.nextInt();
        //check whether the order exists
        query = "SELECT * FROM Orders WHERE orderID="+ordID;
        try{
          ResultSet rs = dbManager.executeQuery(query);
          if(!rs.next()){
            System.out.println("The provided order ID doesn't exist in the DB");
            break;
          }
        }
        catch(Exception e){
          e.printStackTrace();
          System.out.println("Error searching order ID in DB");
          break;
        }
        System.out.println("Enter Amount of Payment: ");
        double payAmt = sc.nextDouble();

        query = "SELECT distID,name,city FROM DistAssigned NATURAL JOIN Distributors WHERE orderID="+ordID;
        try{
          ResultSet rs = dbManager.executeQuery(query);
          if(!rs.next()){
            System.out.println("The provided order ID doesn't exist in the DB");
            break;
          }
          distID = rs.getInt("distID");
          distName = rs.getString("name");
          distCity = rs.getString("city");
        }
        catch(Exception e){
          e.printStackTrace();
          System.out.println("Error searching order ID in DB");
          break;
        }
        System.out.println("The provided order ID corresponds to distributor "+distName+" (ID:"+distID+") from "+distCity+" city.");

        //Get pay ID for Payment
        boolean payFlag = true;
        Random rand = new Random();
        payID = Math.abs(rand.nextInt());
        while(payFlag){
          System.out.println("Enter pay ID for the new payment: ");
          payID = sc.nextInt();
          try{
            query = "SELECT payID from Payment WHERE payID="+payID;
            ResultSet rs = dbManager.executeQuery(query);
            if(!rs.next()){
              payFlag = false;
            }
            else{
              System.out.println("Pay ID already exists. Try again.");
            }
          }
          catch(Exception e){
            System.out.println("Error checking payID. Setting to a random integer");
            payID = Math.abs(rand.nextInt());
          }
        }
        sc.nextLine();
        //Set Payment Date
        System.out.println("Enter date of payment: [YYYY-MM-DD][Leave blank if you want today's date]:");
        String payDate = sc.nextLine();
        if(!checkValidDate(payDate)){
          if(payDate.equals("")){
            payDate = LocalDate.now().toString();
          }
          else{
            System.out.println("Invalid Date Format");
          }
        }

        //Create Payment using transactions to maintain atomicity
        try{
          //start transaction (set auto-commit false)
          dbManager.startTransaction();

          //Step 1: Add to Payment table
          query = "INSERT INTO Payment (payID, generatedDate, amount, claimedDate) VALUES (%d,\"%s\",%f,\"%s\")";
          query = String.format(query,payID,payDate,payAmt,payDate);
          dbManager.executeUpdate(query);

          //Step 2: Associate the payment with the distributor.
          query = "INSERT INTO DistGetsPayment (payID,distID) VALUES (%d,%d)";
          query = String.format(query,payID,distID);
          dbManager.executeUpdate(query);

          //Step 3: Associate the payment with the order
          query = "INSERT INTO PaymentConnectedToOrder (payID, orderID) VALUES (%d,%d)";
          query = String.format(query,payID,ordID);
          dbManager.executeUpdate(query);

          //Step 4: Update the outstanding balance of the distributor
          query = "UPDATE Distributors SET balance=balance-"+payAmt+" WHERE distID="+distID;
          dbManager.executeUpdate(query);

          //If all updates run correctly, commit and set auto-commit back to true
          dbManager.commitTransaction();
          System.out.println("~~~ Successfully updated outstanding balance of Distributor and added Payment details ~~~");
        }
        catch(Exception e){
          //If anything fails, rollback and set auto-commit back to true
          try{
            dbManager.rollbackTransaction();
          }
          catch(Exception ex){
            System.out.println("!!! Error rolling back transaction. Connection to DB Lost. !!!");
          }
          System.out.println("!!! Error in transactions. Rolling back. !!!");
        }
        
        break;

        case 8:
        System.out.println("--------------------------------");
        System.out.println("==> Search for a Distributor <==");
        System.out.println("--------------------------------");
        System.out.println("Using which field do you want to search?");
        System.out.println("1. Distributor ID");
        System.out.println("2. Distributor Name");
        System.out.println("3. Distributor Contact Person");
        System.out.println("4. Distributor Type");
        System.out.println("5. Distributor Street Address");
        System.out.println("6. Distributor City");
        System.out.println("7. Distributor Phone");
        System.out.println("8. Distributor Balance");
        int choices = sc.nextInt();
        sc.nextLine();
        switch(choices){
          case 1:
          System.out.println("Search by Distributor ID: ");
          int searchID = sc.nextInt();
          sc.nextLine();
          query = "SELECT * FROM Distributors WHERE distID="+searchID;
          break;

          case 2:
          System.out.println("Search by Distributor Name: ");
          String searchName = sc.nextLine();
          query = "SELECT * FROM Distributors WHERE name LIKE \"%"+searchName+"%\"";
          break;

          case 3:
          System.out.println("Search by Distributor Contact Person: ");
          String searchPOC = sc.nextLine();
          query = "SELECT * FROM Distributors WHERE contactPerson LIKE \"%"+searchPOC+"%\"";
          break;

          case 4:
          System.out.println("Search by Distributor Type: ");
          String searchType = sc.nextLine();
          query = "SELECT * FROM Distributors WHERE type LIKE \"%"+searchType+"%\"";
          break;

          case 5:
          System.out.println("Search by Distributor Street Address: ");
          String searchAddr = sc.nextLine();
          query = "SELECT * FROM Distributors WHERE strAddr LIKE \"%"+searchAddr+"%\"";
          break;

          case 6:
          System.out.println("Search by Distributor City: ");
          String searchCity = sc.nextLine();
          query = "SELECT * FROM Distributors WHERE city LIKE \"%"+searchCity+"%\"";
          break;

          case 7:
          System.out.println("Search by Distributor Phone: ");
          String searchPhone = sc.nextLine();
          query = "SELECT * FROM Distributors WHERE phone = \""+searchPhone+"\"";
          break;

          case 8:
          System.out.println("Search by Distributor Balance: ");
          double searchBal = sc.nextDouble();
          sc.nextLine();
          query = "SELECT * FROM Distributors WHERE balance = "+searchBal;
          break;
          default: 
          System.out.println("Invalid Choice");
          query = null;
          break;
        }
        if(query != null){
          try{
            ResultSet rs = dbManager.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            int numCol = rsmd.getColumnCount();
            System.out.println("Matching records:");
            while(rs.next()){
              for (int i = 1; i <= numCol; i++) {
                  System.out.println(rsmd.getColumnName(i)+" : "+rs.getString(i));
              }
              System.out.println("");
            }
          }
          catch(Exception e){
            e.printStackTrace();
            System.out.println("!!! Error Searching !!!");
          }
        }
        break;

        default:
        System.out.println("Invalid Choice.");
        break;
      }

      //Repeat?
      System.out.println("");
      System.out.println("Would you like to perform another Distribution task? Enter 1 for Yes or 0 for N");
      choice = sc.nextInt();
      flag = (choice == 1) ? true : false;
    }
  }

 
  public static void reports(){
    //switch case operations
    int flag = 1;
    while(flag)
    {
      System.out.println("Please select the kind of report you would like to generate:");
      System.out.println("1. Monthly report for number of copies and total price of copies per publication per distributor.");
      System.out.println("2. Monthly report on total revenue of the publication house.");
      System.out.println("3. Monthly report of total shipping cost.");
      System.out.println("4. Monthly report of total salary expenses.");
      System.out.println("5. Monthly report of total expenses for the publication house.");
      System.out.println("6. The total current number of distributors.");
      System.out.println("7. The report of total revenue since inception per city.");
      System.out.println("8. The report of total revenue since inception per distributor.");
      System.out.println("9. The report of total revenue since inception per location.");
      System.out.println("10. The report of total payment to editors per time period.");
      System.out.println("11. The report of total payment to author’s per time period.");
      System.out.println("12. The report of total payment to editors and authors for work type.");

      int chosenval = sc.nextInt();
      switch(chosenval)
      {
        // Monthly report for number of copies and total price of copies per publication per distributor
        case 1:
        System.out.println("Enter the start date time (yyyy-mm-dd hh:mm:ss):");
        int start_date = sc.nextInt();
        System.out.println("Enter the end date time (yyyy-mm-dd hh:mm:ss):");
        int end_date = sc.nextInt();
        String query = "SELECT distID, pubID, numCopies, numCopies*price AS total_price FROM ( SELECT orderID, distID, pubID, issueID, IF (orderID IS NULL, NULL, NULL) AS editionID FROM DistAssigned NATURAL JOIN OrderHasIssue UNION SELECT orderID, distID, pubID, IF (orderID IS NULL, NULL, NULL) AS issueID, editionID"+
        "FROM DistAssigned NATURAL JOIN OrderHasEdition) AS DistOrders NATURAL JOIN Orders WHERE date between"+start_date+"and"+end_date+"GROUP BY distID, pubID;";
        dbManager.executeUpdate(query);
        break;
        // Monthly report on total revenue of the publication house
        case 2:
        System.out.println("Enter the start date time (yyyy-mm-dd hh:mm:ss):");
        int start_date = sc.nextInt();
        System.out.println("Enter the end date time (yyyy-mm-dd hh:mm:ss):");
        int end_date = sc.nextInt();
        String query = "SELECT sum(amount) FROM Payment NATURAL JOIN DistGetsPayment WHERE claimedDate is NOT NULL AND claimedDate between"+start_date+"and"+end_date+";";
        dbManager.executeUpdate(query);
        break;
        // Monthly report of total shipping cost
        case 3:
        System.out.println("Enter the start date time (yyyy-mm-dd hh:mm:ss):");
        int start_date = sc.nextInt();
        System.out.println("Enter the end date time (yyyy-mm-dd hh:mm:ss):");
        int end_date = sc.nextInt();
        String query = "SELECT sum(amount) FROM Payment NATURAL JOIN DistGetsPayment WHERE claimedDate is NOT NULL AND claimedDate between"+start_date+"and"+end_date+";";
        dbManager.executeUpdate(query);
        break;
        // Monthly report of total salary expenses
        case 4:
        System.out.println("Enter the start date time (yyyy-mm-dd hh:mm:ss):");
        int start_date = sc.nextInt();
        System.out.println("Enter the end date time (yyyy-mm-dd hh:mm:ss):");
        int end_date = sc.nextInt();
        String query = "SELECT sum(amount) FROM Payment NATURAL JOIN GetsPaid WHERE claimedDate is NOT NULL AND claimedDate between"+ start_date +"and"+end_date+";";
        dbManager.executeUpdate(query);
        break;
        // Monthly report of total expenses for the publication house
        case 5:
        System.out.println("Enter the start date time (yyyy-mm-dd hh:mm:ss):");
        int start_date = sc.nextInt();
        System.out.println("Enter the end date time (yyyy-mm-dd hh:mm:ss):");
        int end_date = sc.nextInt();
        String query = "SELECT SUM(total) FROM( SELECT SUM(shipCost) AS total FROM Orders UNION SELECT SUM(amount) FROM( SELECT GetsPaid.payID, amount FROM GetsPaid INNER JOIN Payment ON GetsPaid.payID = Payment.payID and claimedDate IS NOT NULL WHERE claimedDate between" + start_date + "and" + end_date + ") as SalaryPayment) as TotalExp;";
        dbManager.executeUpdate(query);
        break;
        // The total current number of distributors
        case 6:
        String query = "SELECT count(distID) FROM Distributors;";
        dbManager.executeUpdate(query);
        break;
        // The report of total revenue since inception per city
        case 7:
        String query = "SELECT city,SUM(amount) AS revenue FROM Payment NATURAL JOIN DistGetsPayment NATURAL JOIN Distributors GROUP BY city;";
        dbManager.executeUpdate(query);
        break;
        // The report of total revenue since inception per distributor
        case 8:
        String query = "SELECT distID,name,SUM(amount) AS revenue FROM Payment NATURAL JOIN DistGetsPayment NATURAL JOIN Distributors GROUP BY distID;";
        dbManager.executeUpdate(query);
        break;
        // The report of total revenue since inception per location
        case 9:
        String query = "SELECT SUM(amount), city FROM Payment NATURAL JOIN DistGetsPayment NATURAL JOIN Distributors GROUP BY city;";
        dbManager.executeUpdate(query);
        break;
        // The report of total payment to editors per time period
        case 10:
        System.out.println("Enter the start date time (yyyy-mm-dd hh:mm:ss):");
        int start_date = sc.nextInt();
        System.out.println("Enter the end date time (yyyy-mm-dd hh:mm:ss):");
        int end_date = sc.nextInt();
        String query = "SELECT SUM(amount) AS total_payment FROM Payment NATURAL JOIN GetsPaid NATURAL JOIN Editor WHERE claimedDate IS NOT NULL AND claimedDate between"+start_date+"and"+end_date+";";
        dbManager.executeUpdate(query);
        break;
        // The report of total payment to author’s per time period
        case 11:
        System.out.println("Enter the start date time (yyyy-mm-dd hh:mm:ss):");
        int start_date = sc.nextInt();
        System.out.println("Enter the end date time (yyyy-mm-dd hh:mm:ss):");
        int end_date = sc.nextInt();
        String query = "SELECT SUM(amount) AS total_payment FROM Payment NATURAL JOIN GetsPaid NATURAL JOIN Author WHERE claimedDate IS NOT NULL AND claimedDate between"+start_date+"and"+end_date+";";
        dbManager.executeUpdate(query);
        break;
        // The report of total payment to editors and authors for work type
        case 12:
        String query = "SELECT IF(staffID in (SELECT staffID FROM Editor),'editorial work', IF(staffID in (SELECT staffID FROM AuthorAssignedEdition), 'book authorship','article authorship')) AS workType, SUM(amount) as totalPayment FROM Payment NATURAL JOIN GetsPaid GROUP BY workType;";
        dbManager.executeUpdate(query);
        break;
        default: System.out.println("Please enter a valid choice.");
        break;
      }
    }
  }
  public static String findUserClass(DBManager dbManager, String username, String password) throws SQLException{
    //Check Staff table for correct credentials
    String query = "SELECT * from Staff where username = \""+username+"\"";
    ResultSet rs = dbManager.executeQuery(query);
    int staffID = -1;
    if(rs != null){
      //Check correct password
      while(rs.next()){
        String dbpass = rs.getString("password");
        if (password.equals(dbpass)){
          staffID = rs.getInt("staffID");
        }
        else{
          System.out.println("Invalid Credentials");
          System.exit(0);
        }
        break;
      }      
    }
    else{
      System.out.println("Invalid Credentials");
      System.exit(0);
    }

    if(staffID!=-1) {
      //search editor
      query = "SELECT staffID FROM Editor WHERE staffID = \"" + staffID+"\"";
      rs = dbManager.executeQuery(query);
      if(rs.next() == true) {
        return "Editor";
      }
      //search author
      query = "SELECT staffID FROM Author WHERE staffID = \"" + staffID+"\"";
      rs = dbManager.executeQuery(query);
      if(rs.next() == true) {
        return "Author";
      }
      //search pubmng
      query = "SELECT staffID FROM PublicationManager WHERE staffID = \"" + staffID+"\"";
      rs = dbManager.executeQuery(query);
      if(rs.next() == true) {
        return "PublicationManager";
      }
      //search distmng
      query = "SELECT staffID FROM DistributionManager WHERE staffID = \"" + staffID+"\"";
      rs = dbManager.executeQuery(query);
      if(rs.next() == true) {
        return "DistributionManager";
      }
      //search distributor
      //distID is not staffID
      // query = "SELECT staffID FROM Distributors WHERE staffID = \"" + staffID+"\"";
      // rs = dbManager.executeQuery(query);
      // if(rs!=null) {
      //   return "Distributors";
      // }
      return "Admin";
    }
    return null;
  }
  public static boolean checkValidDate(String date){
    DateTimeFormatter fomatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
    try{
      LocalDate ld = LocalDate.parse(date, fomatter);
      String result = ld.format(fomatter);
      return result.equals(date);
    }
    catch(DateTimeParseException e){
      
    }
    return false;
  }
} 