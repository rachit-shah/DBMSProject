import java.util.*;
import java.util.regex.Pattern;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

class Main {
  public static void main(String[] args) throws SQLException{
    Scanner sc = new Scanner(System.in);
    DBManager dbManager = new DBManager();
    try{
      dbManager.init(); //Create database and tables
      dbManager.insertUsers();
      try{
        //dbManager.insertSampleData();
      }
      catch(Exception ex){
        System.out.println("Data already inserted.");
      }
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
    System.out.println("You are signed in as a "+userType);
    if(userType == null){
      sc.close();
      return;
    }
    boolean flag = true;
    int choice;
    while(flag){
      try{
        switch(userType){
          case "Admin":
          System.out.println("Determine what you want to do:");
          System.out.println("1. Editing and Publishing");
          System.out.println("2. Production");
          System.out.println("3. Distribution");
          System.out.println("4. Reports");
          System.out.println("5. Manage Personnel");
          choice = sc.nextInt();
          switch(choice){
            case 1: editingAndPublishing(dbManager,sc); break;
            case 2: production(dbManager,sc); break;
            case 3: distribution(dbManager,sc); break;
            case 4: reports(dbManager,sc); break;
            case 5: personnel(dbManager,sc); break;
            default: System.out.println("Invalid Choice");
            break;
          }
          break;
          
          case "PublicationManager":
          System.out.println("Determine what you want to do:");
          System.out.println("1. Editing and Publishing");
          System.out.println("2. Production");
          choice = sc.nextInt();
          switch(choice){
            case 1: editingAndPublishing(dbManager,sc); break;
            case 2: production(dbManager,sc); break;
            default: System.out.println("Invalid Choice");
            break;
          }
          break;

          case "DistributionManager":
          distribution(dbManager,sc);
          break;

          case "Editor":
          System.out.println("Determine what you want to do:");
          System.out.println("1. Editing and Publishing");
          System.out.println("2. Production");
          choice = sc.nextInt();
          switch(choice){
            case 1: editingAndPublishing(dbManager,sc); break;
            case 2: production(dbManager,sc); break;
            default: System.out.println("Invalid Choice");
            break;
          }
          break;

          case "Author":
          System.out.println("Determine what you want to do:");
          System.out.println("1. Editing and Publishing");
          System.out.println("2. Production");
          choice = sc.nextInt();
          switch(choice){
            case 1: editingAndPublishing(dbManager,sc); break;
            case 2: production(dbManager,sc); break;
            default: System.out.println("Invalid Choice");
            break;
          }
          break;

          default:
          break;
        }
      }
      catch(Exception e){
        System.out.println("Invalid Input");
      }
      //Repeat?
      System.out.println("");
      System.out.println("Would you like to perform another task? Enter 1 for Yes or 0 for N");
      int choicerepeat = sc.nextInt();
      sc.nextLine();
      flag = (choicerepeat == 1) ? true : false;
    }
    dbManager.closeConnection();
    sc.close();
  }

  public static void editingAndPublishing(DBManager dbManager, Scanner sc){
          //switch case operations for the editing and publishing tasks
        int flag15 = 1;
        while (flag15 == 1) // while loop to repeatedly ask the user to perfrom editing and publishing tasks
        {
            try {

                // Differenct operations for the user to perform.	
                System.out.println("Determine what Editing and Publishing activity you want to perfrom:");
                System.out.println("1.  Add Publication");
                System.out.println("2.  Update Publication");
                System.out.println("3.  Delete Publication");
                System.out.println("4.  Add Article");
                System.out.println("5.  Update Article");
                System.out.println("6.  Delete Article");
                System.out.println("7.  Add Chapter");
                System.out.println("8.  Update Chapter");
                System.out.println("9.  Delete Chapter");
                System.out.println("10. Add editor to publication");
                System.out.println("11. Delete editor from publication");
                System.out.println("12. View assigned publication");
                System.out.println("13. View tableofcontent - issue of magazine or jounral for a publication");
                System.out.println("14. View tableofcontent - edition of book for a publication");
                System.out.println("15. Update tableofcontent - issue of magazine or journal for a publication");
                System.out.println("16. Update tableofcontent - edition of book for a publication");
                System.out.println("17. Search Database");
                int chosenval = sc.nextInt();
                sc.nextLine();

                //declaring all the required variables
                ResultSet rs;
                int id, pubId, artId, check, check1, check2, check3, check4, chosenval1, choice1, chapId, issueId, editionId, flag, loopcontrol, loopcontrol1 = 1, bflag, jflag, mflag, yesflag, articlenum, chapid, chapternum, editorId;
                String title, date, text, topic, periodicity, query;

                //starting the switch case operations.
                switch (chosenval) {

                    //enter publication 1.books 2.magazine 3.Journal
                    case 1:
                        System.out.println("What type of publication do you want to enter? Choose 1.Book 2.Magazine 3.Journal");
                        chosenval1 = sc.nextInt();
                        sc.nextLine();

                        //switch case to choose between the 1.Book 2.Magazine 3.Journal
                        switch (chosenval1) {
                            case 1: //Book
                                try {
                                    dbManager.startTransaction();
                                    System.out.println("Enter the publication ID of the book publication to insert");
                                    pubId = sc.nextInt();
                                    sc.nextLine();
                                    System.out.println("Enter the title of the publication to insert");
                                    title = sc.nextLine();
									query = "select pubID from Publication";
									rs = dbManager.executeQuery(query);
									flag = 0;
									while(rs.next())
									{
										if(pubId == rs.getInt("pubID"))
										{
											flag = 1;
										}
									}
									if(flag ==1)
									{
										System.out.println("The publication ID is already present in the database, Sorry");
										break;
									}
                                    query = "INSERT INTO Publication(pubID,title)VALUES(" + pubId + ",\'" + title + "');"; //insert statement for the publication
                                    dbManager.executeUpdate(query);
                                    System.out.println("Do you want to enter the topic of the book publication? Choose 1.Yes 0.No");
                                    check = sc.nextInt();
                                    sc.nextLine();
                                    topic = "";
                                    //helps check if the topic of the publication is required to be entered by the user.
                                    if (check == 1) {
                                        System.out.println("Enter the topic of the publication");
                                        topic = sc.nextLine();
                                    }
                                    //inserting the books and performing the commit operation of transaction
                                    query = "INSERT INTO Books(pubID,topic)VALUES(" + pubId + ",\'" + topic + "')";
                                    dbManager.executeUpdate(query);
                                    dbManager.commitTransaction();
                                    System.out.println("The Book publication was successfully inserted");
                                }
                                //catch statement to check if any errors occured during the transaction
                                catch (SQLException e) {
                                    try {
                                        dbManager.rollbackTransaction();
                                    } catch (Exception ex4) {
                                        System.out.println("Error in rolling back transation");
                                    }
                                    System.out.println("Error in inserting the publication");
                                }
                                break;

                            case 2: // Magazine publication insert
                                try {
                                    dbManager.startTransaction();
                                    System.out.println("Enter the publication ID of the Magazine publication to insert");
                                    pubId = sc.nextInt();
                                    sc.nextLine();
                                    System.out.println("Enter the title of the magazine publication to insert");
                                    title = sc.nextLine();
									query = "select pubID from Publication";
									rs = dbManager.executeQuery(query);
									flag = 0;
									while(rs.next())
									{
										if(pubId == rs.getInt("pubID"))
										{
											flag = 1;
										}
									}
									if(flag ==1)
									{
										System.out.println("The publication ID is already present in the database, Sorry");
										break;
									}
                                    query = "INSERT INTO Publication(pubID,title)VALUES(" + pubId + ",\'" + title + "');";
                                    dbManager.executeUpdate(query);
                                    System.out.println("Do you want to enter the periodicity of the magazine publication? Choose 1.Yes 0.No");
                                    check = sc.nextInt();
                                    sc.nextLine();
                                    periodicity = "";
                                    //checking if user wants to enter the periodicity of the magazine
                                    if (check == 1) {
                                        System.out.println("enter the periodicity of the publication");
                                        periodicity = sc.nextLine();
                                    }
                                    query = "INSERT INTO Magazines(pubID,periodicity)VALUES(" + pubId + ",\'" + periodicity + "')"; // inserting into the magazine
                                    dbManager.executeUpdate(query);
                                    dbManager.commitTransaction();
                                    System.out.println("The Magazine publication was successfully inserted");
                                }
                                //catch statement to check if any transaction realted errors occures
                                catch (SQLException e) {
                                    try {
                                        dbManager.rollbackTransaction();
                                    } catch (Exception ex1) {
                                        System.out.println("Error in rolling back transation");
                                    }
                                    System.out.println("Error in inserting the publication");
                                }

                                break;

                            case 3: //jounrals
                                try {
                                    dbManager.startTransaction();
                                    System.out.println("Enter the publication ID of the Jounral publication to insert");
                                    pubId = sc.nextInt();
                                    sc.nextLine();
                                    System.out.println("Enter the title of the Journal publication to insert");
                                    title = sc.nextLine();
									query = "select pubID from Publication";
									rs = dbManager.executeQuery(query);
									flag = 0;
									while(rs.next())
									{
										if(pubId == rs.getInt("pubID"))
										{
											flag = 1;
										}
									}
									if(flag ==1)
									{
										System.out.println("The publication ID is already present in the database, Sorry");
										break;
									}
                                    query = "INSERT INTO Publication(pubID,title)VALUES(" + pubId + ",\'" + title + "');"; // inserting into the jounral publication
                                    dbManager.executeUpdate(query);
                                    System.out.println("Do you want to enter the periodicity of the journal publication? Choose 1.Yes 0.No");
                                    check = sc.nextInt();
                                    sc.nextLine();
                                    periodicity = "";
                                    //checking if the user wants to enter the periodicity values
                                    if (check == 1) {
                                        System.out.println("enter the periodicity of the publication");
                                        periodicity = sc.nextLine();
                                    }
                                    //inserting into the journals publication
                                    query = "INSERT INTO Journals(pubID,periodicity)VALUES(" + pubId + ",\'" + periodicity + "')";
                                    dbManager.executeUpdate(query);
                                    //commiting the transaction
                                    dbManager.commitTransaction();
                                    System.out.println("The Journal publication was successfully inserted");
                                }
                                //catch statement to check for errors with the transaction
                                catch (SQLException e) {
                                    try {
                                        dbManager.rollbackTransaction();
                                    } catch (Exception ex47) {
                                        System.out.println("Error in rolling back transation");
                                    }
                                    System.out.println("Error in inserting the publication");
                                }

                                break;
                        }
                        break;



                        //edit publication details option of the switch case
                    case 2:
                        loopcontrol = 0; // variable to check if publication id is entered fine or else it will ask the user to enter it again
                        pubId = 0;
                        try {
                            dbManager.startTransaction(); // starting the transaction
                            //loop to check if publication id entered by user is present.
                            while (loopcontrol == 0) {
                                //Enter the publication id to update	  
                                System.out.println("Enter the publication ID of the publication to update");
                                pubId = sc.nextInt();
                                sc.nextLine();
                                query = "select pubID from Publication";
                                rs = dbManager.executeQuery(query);
                                flag = 0;
                                //loop to check if the pubid exists from the resultset
                                while (rs.next()) {
                                    if (rs.getInt("pubID") == pubId) {
                                        flag = 1;
                                        loopcontrol = 1;
                                    }
                                }
                                //based on presence the loop exits or reasks the user to enter
                                if (flag != 1) {
                                    System.out.println("Publication ID entered doesnot exist, Please re-enter");
                                    System.out.println("Do you want to reenter the publicationId? 1.Yes 0.NO");
                                    check3 = sc.nextInt();
                                    sc.nextLine();
                                    if (check3 == 0) {
                                        loopcontrol = 0;
                                        loopcontrol1 = 0;
                                        break;
                                    }
                                }
                            }
                            //if pubd id doesnt exist and user doesnt want to re-renter then following condition matches
                            if (loopcontrol1 == 0) {
                                break;
                            }
                            bflag = 0;
                            jflag = 0;
                            mflag = 0;
                            query = "select pubId from Books where pubID=" + pubId + ";";
                            //while loop to check if book 
                            rs = dbManager.executeQuery(query);
                            while (rs.next()) {
                                if (rs.getInt("pubID") == pubId) {
                                    bflag = 1;
                                }
                            }
                            query = "select pubId from Magazines where pubID=" + pubId + ";";
                            rs = dbManager.executeQuery(query);
                            //while loop to check if magazine 
                            while (rs.next()) {
                                if (rs.getInt("pubID") == pubId) {
                                    mflag = 1;
                                }
                            }
                            query = "select pubId from Journals where pubID=" + pubId + ";";
                            rs = dbManager.executeQuery(query);
                            //while loop to check if journal 
                            while (rs.next()) {
                                if (rs.getInt("pubID") == pubId) {
                                    jflag = 1;
                                }
                            }


                            //if it is a book publication
                            if (bflag == 1) {
                                System.out.println("You are editing the Book publication");
                                System.out.println("Do you want to update the title of the publication? 1.yes 0.No");
                                yesflag = sc.nextInt();
                                sc.nextLine();
                                //checks for title
                                if (yesflag == 1) {
                                    System.out.println("Enter the title of the publication to update");
                                    title = sc.nextLine();
                                    query = "UPDATE Publication SET title ='" + title + "'WHERE pubID=" + pubId + ";";
                                    dbManager.executeUpdate(query);
                                    //System.out.println("The publication was successfully updated");
                                }
                                System.out.println("Do you want to update the topic of the publication? 1.yes 0.No");
                                yesflag = sc.nextInt();
                                sc.nextLine();
                                //checks for topic
                                if (yesflag == 1) {
                                    System.out.println("Enter the topic of the publication to update");
                                    topic = sc.nextLine();
                                    query = "UPDATE Books SET topic ='" + topic + "'WHERE pubID=" + pubId + ";";
                                    dbManager.executeUpdate(query);
                                    //System.out.println("The publication was successfully updated");
                                }
                            }


                            //if it is a magazine publication
                            if (mflag == 1) {
                                System.out.println("You are editing the Magazine publication");
                                System.out.println("Do you want to update the title of the publication? 1.yes 0.No");
                                yesflag = sc.nextInt();
                                sc.nextLine();
                                //checks for title
                                if (yesflag == 1) {
                                    System.out.println("Enter the title of the publication to update");
                                    title = sc.nextLine();
                                    query = "UPDATE Publication SET title ='" + title + "'WHERE pubID=" + pubId + ";";
                                    dbManager.executeUpdate(query);
                                    //System.out.println("The publication was successfully updated");
                                }
                                System.out.println("Do you want to update the periodicity of the publication? 1.yes 0.No");
                                yesflag = sc.nextInt();
                                sc.nextLine();
                                //checks for periodicity
                                if (yesflag == 1) {
                                    System.out.println("Enter the periodicity of the publication to update");
                                    periodicity = sc.nextLine();
                                    query = "UPDATE Magazines SET periodicity ='" + periodicity + "'WHERE pubID=" + pubId + ";";
                                    dbManager.executeUpdate(query);
                                    //System.out.println("The publication was successfully updated");
                                }
                            }
                            //checks for journal insertion
                            if (jflag == 1) {
                                System.out.println("You are editing the Jounral publication");
                                System.out.println("Do you want to update the title of the publication? 1.yes 0.No");
                                yesflag = sc.nextInt();
                                sc.nextLine();
                                //checks for title
                                if (yesflag == 1) {
                                    System.out.println("Enter the title of the publication to update");
                                    title = sc.nextLine();
                                    query = "UPDATE Publication SET title ='" + title + "'WHERE pubID=" + pubId + ";";
                                    dbManager.executeUpdate(query);
                                    //System.out.println("The publication was successfully updated");
                                }
                                System.out.println("Do you want to update the periodicity of the publication? 1.yes 0.No");
                                yesflag = sc.nextInt();
                                sc.nextLine();
                                //checks for periodicity
                                if (yesflag == 1) {
                                    System.out.println("Enter the periodicity of the publication to update");
                                    periodicity = sc.nextLine();
                                    query = "UPDATE Journals SET periodicity ='" + periodicity + "'WHERE pubID=" + pubId + ";";
                                    dbManager.executeUpdate(query);
                                    //System.out.println("The publication was successfully updated");
                                }
                            }
                            dbManager.commitTransaction();
                            System.out.println("The publication was successfully updated");
                        } catch (Exception e) {
                            System.out.println(e);
                            try {
                                dbManager.rollbackTransaction();
                            } catch (Exception ex56) {
                                System.out.println("Error rolling back transaction");
                            }
                            System.out.println("Error executing update");
                        }
                        break;

                        //delete publication
                    case 3:
                        loopcontrol = 0; //checks for pubid being the db
                        pubId = 0;
                        //loop to check for the pubid
                        while (loopcontrol == 0) {
                            System.out.println("Enter the publication ID of the publication to delete");
                            pubId = sc.nextInt();
                            sc.nextLine();
                            query = "select pubID from Publication";
                            rs = dbManager.executeQuery(query);
                            flag = 0;
                            while (rs.next()) {
                                if (rs.getInt("pubID") == pubId) {
                                    flag = 1;
                                    loopcontrol = 1;
                                }
                            }
                            if (flag != 1) {
                                System.out.println("Publication ID entered doesnot exist, Please re-enter");
                                System.out.println("Do you want to reenter the publicationId? 1.Yes 0.NO");
                                check3 = sc.nextInt();
                                sc.nextLine();
                                if (check3 == 0) {
                                    loopcontrol = 0;
                                    loopcontrol1 = 0;
                                    break;
                                }
                            }
                        }
                        if (loopcontrol1 == 0) {
                            break;
                        }
                        query = "DELETE FROM Publication WHERE pubID =" + pubId + ";";
                        dbManager.executeUpdate(query);
                        System.out.println("The publication was successfully deleted");
                        break;



                        //inserting article
                    case 4:
                        try {
							dbManager.startTransaction();
                            title = "";
                            date = LocalDate.now().toString(); //default date of date
                            text = "";
                            System.out.println("Enter the article ID of the article to insert");
                            artId = sc.nextInt();
                            sc.nextLine();
                            System.out.println("Enter the topic of the article to insert");
                            topic = sc.nextLine();
						    query = "select artID from Article";
							rs = dbManager.executeQuery(query);
							flag = 0;
							while(rs.next())
								{
									if(artId == rs.getInt("artID"))
										{
											flag = 1;
										}
								}
							if(flag ==1)
								{
									System.out.println("The Article ID is already present in the database, Sorry");
									break;
								}
                            System.out.println("Do you want to enter the title of the article? Enter 1 for Yes or 0 for No");
                            check = sc.nextInt();
                            sc.nextLine();
                            //check if title needs to be inserted
                            if (check == 1) {
                                System.out.println("Enter the title of the article to insert");
                                title = sc.nextLine();
                            }
                            System.out.println("Do you want to enter the date of the article? Enter 1 for Yes or 0 for No");
                            check1 = sc.nextInt();
                            sc.nextLine();
                            //checks if date needs to be inserted
                            if (check1 == 1) {
                                flag = 0;
                                while (flag == 0) {
                                    System.out.println("Enter the date of the article to insert,Please enter the date in YYYY-MM-DD");
                                    date = sc.nextLine();
                                    if (!checkValidDate(date)) {
                                        if (date.equals("")) {
                                            date = LocalDate.now().toString();
                                        } else {
                                            System.out.println("Invalid Date Format,Please re-enter the date");
                                            continue;
                                        }
                                    }
                                    flag = 1;
                                }
                            }
                            System.out.println("Do you want to enter the text of the article? Enter 1 for Yes or 0 for No");
                            check2 = sc.nextInt();
                            sc.nextLine();
                            //checks if text needs to be entered
                            if (check2 == 1) {
                                System.out.println("Enter the text of the article to insert");
                                text = sc.nextLine();
                            }
                            String query1 = "INSERT INTO Article(artID, topic, title, date,text) VALUES(" + artId + ",'" + topic + "','" + title + "','" + date + "','" + text + "');";
                            System.out.println("Please also insert into the IssueContains table in order to associate article with journal or magazine to give current status");
                            System.out.println("Please enter the publication ID");
                            pubId = sc.nextInt();
                            sc.nextLine();
                            System.out.println("Please enter the issue ID");
                            issueId = sc.nextInt();
                            sc.nextLine();
                            System.out.println("Please enter the article number");
                            articlenum = sc.nextInt();
                            sc.nextLine();
                            query = "INSERT INTO IssueContains(pubID, issueID,artID, artNum) VALUES(" + pubId + "," + issueId + "," + artId + "," + articlenum + ");";
                            dbManager.executeUpdate(query1);
                            dbManager.executeUpdate(query);
							dbManager.commitTransaction();
                            System.out.println("article was successfully inserted and issueContains table was successfully updated");
                        } catch (Exception e) {
                            try {
                                dbManager.rollbackTransaction();
                            } catch (Exception ex45) {
                                System.out.println("the rollback was not successful");
                            }
                            System.out.println("The article was not inserted successfully");
                        }
                        break;



                        //updating article
                    case 5:
                        loopcontrol = 0;
                        artId = 0;
                        //loop to check if articleid exists
                        while (loopcontrol == 0) {
                            System.out.println("Enter the article id you want to update");
                            artId = sc.nextInt();
                            sc.nextLine();
                            query = "select artID from Article";
                            rs = dbManager.executeQuery(query);
                            flag = 0;
                            //checks article id from db
                            while (rs.next()) {
                                if (rs.getInt("artID") == artId) {
                                    flag = 1;
                                    loopcontrol = 1;
                                }
                            }
                            if (flag != 1) {
                                System.out.println("Article ID entered doesnot exist, Please re-enter");
                                System.out.println("Do you want to reenter the ArticleID? 1.Yes 0.NO");
                                check3 = sc.nextInt();
                                sc.nextLine();
                                if (check3 == 0) {
                                    loopcontrol = 0;
                                    loopcontrol1 = 0;
                                    break;
                                }
                            }
                        }
                        if (loopcontrol1 == 0) {
                            break;
                        }
                        chosenval1 = 1;
                        //switch case to choose the various operations available.
                        while (chosenval1 == 1) {
                            System.out.println("Determine what you want to do in updating article");
                            System.out.println("1. update topic");
                            System.out.println("2. update title");
                            System.out.println("3. update date");
                            System.out.println("4. update text");
                            System.out.println("5.Update the articleNum for associated with publication");
                            choice1 = sc.nextInt();
                            sc.nextLine();
                            //swithcing between the various options
                            switch (choice1) {
                                //topic
                                case 1:
                                    System.out.println("Enter the topic of the article to update");
                                    topic = sc.nextLine();
                                    query = "UPDATE Article SET topic ='" + topic + "'WHERE artID=" + artId + ";";
                                    dbManager.executeUpdate(query);
                                    System.out.println("The article was successfully updated");
                                    break;
                                    //title
                                case 2:
                                    System.out.println("Enter the title of the article to update");
                                    title = sc.nextLine();
                                    query = "UPDATE Article SET title ='" + title + "'WHERE artID=" + artId + ";";
                                    dbManager.executeUpdate(query);
                                    System.out.println("The article was successfully updated");
                                    break;
                                    //date
                                case 3:
                                    System.out.println("Enter the date of the article to update - Please enter the date in YYYY-MM-DD");
                                    date = sc.nextLine();
                                    flag = 0;
                                    while (flag == 0) {
                                        System.out.println("Enter the date of the article to insert,Please enter the date in YYYY-MM-DD");
                                        date = sc.nextLine();
                                        if (!checkValidDate(date)) {
                                            if (date.equals("")) {
                                                date = LocalDate.now().toString();
                                            } else {
                                                System.out.println("Invalid Date Format,Please re-enter the date");
                                                continue;
                                            }
                                        }
                                        flag = 1;
                                    }
                                    query = "UPDATE Article SET date ='" + date + "'WHERE artID=" + artId + ";";
                                    dbManager.executeUpdate(query);
                                    System.out.println("The article was successfully updated");
                                    break;
                                    //article
                                case 4:
                                    System.out.println("Enter the text of the article to update");
                                    text = sc.nextLine();
                                    query = "UPDATE Article SET text ='" + text + "'WHERE artID=" + artId + ";";
                                    dbManager.executeUpdate(query);
                                    System.out.println("The article was successfully updated");
                                    break;
                                    //article num
                                case 5:
                                    System.out.println("Enter the article number you want");
                                    articlenum = sc.nextInt();
                                    sc.nextLine();
                                    query = "UPDATE IssueContains SET artNum =" + articlenum + "WHERE artID=" + artId + ";";
                                    dbManager.executeUpdate(query);
                                    System.out.println("The article was successfully updated");
                                default:
                                    System.out.println("invalid option entered");
                                    break;
                            }
                            System.out.println("Do you want to update other fields of article? select 1 for yes 0 for no");
                            chosenval1 = sc.nextInt();
                            sc.nextLine();
                        }
                        break;



                        //delete an article
                    case 6:
                        loopcontrol = 0;
                        artId = 0;
                        //loop to check if article id is fine
                        while (loopcontrol == 0) {
                            System.out.println("Enter the article id you want to delete");
                            artId = sc.nextInt();
                            sc.nextLine();
                            query = "select artID from Article";
                            rs = dbManager.executeQuery(query);
                            flag = 0;
                            //getting article id from db
                            while (rs.next()) {
                                if (rs.getInt("artID") == artId) {
                                    flag = 1;
                                    loopcontrol = 1;
                                }
                            }
                            if (flag != 1) {
                                System.out.println("article ID entered doesnot exist, Please re-enter");
                                System.out.println("Do you want to reenter the articleID? 1.Yes 0.NO");
                                check3 = sc.nextInt();
                                sc.nextLine();
                                if (check3 == 0) {
                                    loopcontrol = 0;
                                    loopcontrol1 = 0;
                                    break;
                                }
                            }
                        }
                        if (loopcontrol1 == 0) {
                            break;
                        }
                        //query to delete article
                        query = "DELETE FROM Article WHERE artID=" + artId + ";";
                        dbManager.executeUpdate(query);
                        System.out.println("The article was successfuly deleted");
                        break;


                        //insert chapter
                    case 7:
                        try {
							dbManager.startTransaction();
                            date = LocalDate.now().toString(); // default date value
                            text = "";
                            System.out.println("Enter the chapter ID of the chapter to insert");
                            chapId = sc.nextInt();
                            sc.nextLine();
                            System.out.println("Enter the title of the chapter to insert");
                            title = sc.nextLine();
							query = "select chapID from Chapter";
							rs = dbManager.executeQuery(query);
							flag = 0;
							while(rs.next())
								{
									if(chapId == rs.getInt("chapID"))
										{
											flag = 1;
										}
								}
							if(flag ==1)
								{
									System.out.println("The chapter ID is already present in the database, Sorry");
									break;
								}
                            System.out.println("Do you want to enter the date of the chapter? Enter 1 for Yes or 0 for No");
                            check3 = sc.nextInt();
                            sc.nextLine();
                            //check values for date
                            if (check3 == 1) {
                                flag = 0;
                                while (flag == 0) {
                                    System.out.println("Enter the date of the article to insert,Please enter the date in YYYY-MM-DD");
                                    date = sc.nextLine();
                                    if (!checkValidDate(date)) {
                                        if (date.equals("")) {
                                            date = LocalDate.now().toString();
                                        } else {
                                            System.out.println("Invalid Date Format,Please re-enter the date");
                                            continue;
                                        }
                                    }
                                    flag = 1;
                                }
                            }
                            System.out.println("Do you want to enter the text of the chapter? Enter 1 for Yes or 0 for No");
                            check4 = sc.nextInt();
                            sc.nextLine();
                            //check for text
                            if (check4 == 1) {
                                System.out.println("Enter the text of the chapter to insert");
                                text = sc.nextLine();
                            }
                            String query2 = "INSERT INTO Chapter(chapID, title, date, text) VALUES(" + chapId + ",'" + title + "','" + date + "','" + text + "');";
                            System.out.println("Please also insert into the EditionContains table to link chapter with book so that we can get the required status");
                            System.out.println("Please enter the publication ID");
                            pubId = sc.nextInt();
                            sc.nextLine();
                            System.out.println("Please enter the edition ID");
                            editionId = sc.nextInt();
                            sc.nextLine();
                            System.out.println("Please enter the chapterNum number");
                            chapternum = sc.nextInt();
                            sc.nextLine();
                            query = "INSERT INTO EditionContains(pubID, editionID,chapID, chapNum) VALUES(" + pubId + "," + editionId + "," + chapId + "," + chapternum + ");";
                            dbManager.executeUpdate(query2);
                            dbManager.executeUpdate(query);
                            System.out.println("Values inserted into editionContains table successfully");
							dbManager.commitTransaction();
                        } catch (Exception e) {
                            try {
                                dbManager.rollbackTransaction();
                            } catch (Exception ex8) {
                                System.out.println("Rollback failed");
                            }
                            System.out.println("The update was not successful for the chapter");
                        }
                        break;


                        //update chapter
                    case 8:
                        loopcontrol = 0;
                        chapid = 0;
                        title = "";
                        text = "";
                        date = LocalDate.now().toString();
                        //checking if chapterid is present
                        while (loopcontrol == 0) {
                            System.out.println("Enter the chapter id you want to update");
                            chapid = sc.nextInt();
                            sc.nextLine();
                            query = "select chapID from Chapter";
                            rs = dbManager.executeQuery(query);
                            flag = 0;
                            //checking chapter id from db
                            while (rs.next()) {
                                if (rs.getInt("chapID") == chapid) {
                                    flag = 1;
                                    loopcontrol = 1;
                                }
                            }
                            if (flag != 1) {
                                System.out.println("chapter ID entered doesnot exist, Please re-enter");
                                System.out.println("Do you want to reenter the chapterid? 1.Yes 0.NO");
                                check3 = sc.nextInt();
                                sc.nextLine();
                                if (check3 == 0) {
                                    loopcontrol = 0;
                                    loopcontrol1 = 0;
                                    break;
                                }
                            }
                        }
                        int chosenval5 = 1;
                        //switching based on the chosen values
                        while (chosenval5 == 1) {
                            System.out.println("Determine what you want to do in updating chapter");
                            System.out.println("1. update title");
                            System.out.println("2. update date");
                            System.out.println("3. update text");
                            System.out.println("4. Update the chapterNumber filed for associated publication Book");
                            choice1 = sc.nextInt();
                            sc.nextLine();
                            //swtiching on the values
                            switch (choice1) {
                                //title
                                case 1:
                                    System.out.println("Enter the title of the chapter to update");
                                    title = sc.nextLine();
                                    query = "UPDATE Chapter SET title ='" + title + "'WHERE chapID=" + chapid + ";";
                                    dbManager.executeUpdate(query);
                                    System.out.println("Updated chapter successfully");
                                    break;
                                    //date
                                case 2:
                                    System.out.println("Enter the date of the chapter to update,please enter in YYYY-MM-DD");
                                    date = sc.nextLine();
                                    query = "UPDATE Chapter SET date ='" + date + "'WHERE chapID=" + chapid + ";";
                                    dbManager.executeUpdate(query);
                                    System.out.println("Updated chapter successfully");
                                    break;
                                case 3:
                                    System.out.println("Enter the text of the chapter to update");
                                    text = sc.nextLine();
                                    query = "UPDATE Chapter SET text ='" + text + "'WHERE chapID=" + chapid + ";";
                                    dbManager.executeUpdate(query);
                                    System.out.println("Updated chapter successfully");
                                    break;
                                    //chapter num
                                case 4:
                                    System.out.println("Enter the chpater number to update");
                                    chapternum = sc.nextInt();
                                    sc.nextLine();
                                    query = "UPDATE EditionContains SET chapNum =" + chapternum + "WHERE chapID=" + chapid + ";";
                                    dbManager.executeUpdate(query);
                                    System.out.println("Updated chapter successfully");
                                default:
                                    System.out.println("invalid option entered");
                                    break;
                            }
                            System.out.println("Do you want to update other fields of chapter enter 1 for yes 0 for no");
                            chosenval5 = sc.nextInt();
                            sc.nextLine();
                        }


                        break;





                        //delete chapter
                    case 9:
                        loopcontrol = 0;
                        chapId = 0;
                        //loop to check if value is present for chapter id
                        while (loopcontrol == 0) {
                            System.out.println("Enter the chapter id you want to update");
                            chapId = sc.nextInt();
                            sc.nextLine();
                            query = "select chapID from Chapter";
                            rs = dbManager.executeQuery(query);
                            flag = 0;
                            while (rs.next()) {
                                if (rs.getInt("chapID") == chapId) {
                                    flag = 1;
                                    loopcontrol = 1;
                                }
                            }
                            if (flag != 1) {
                                System.out.println("chapter ID entered doesnot exist, Please re-enter");
                                System.out.println("Do you want to reenter the chapter? 1.Yes 0.NO");
                                check3 = sc.nextInt();
                                sc.nextLine();
                                if (check3 == 0) {
                                    loopcontrol = 0;
                                    loopcontrol1 = 0;
                                    break;
                                }
                            }
                        }
                        //deleting the chapter
                        query = "DELETE FROM Chapter WHERE chapID=" + chapId + ";";
                        dbManager.executeUpdate(query);
                        System.out.println("The Chapter was deleted successfully");
                        break;


                        //assigning editor to publication
                    case 10:
                        loopcontrol = 0;
                        editorId = 0;
                        //checking if the editor id is present
                        while (loopcontrol == 0) {
                            System.out.println("Enter the editor id you want to assign");
                            editorId = sc.nextInt();
                            sc.nextLine();
                            query = "select staffID from Editor";
                            rs = dbManager.executeQuery(query);
                            flag = 0;
                            //checkinf the statff id exists
                            while (rs.next()) {
                                if (rs.getInt("StaffID") == editorId) {
                                    flag = 1;
                                    loopcontrol = 1;
                                }
                            }
                            if (flag != 1) {
                                System.out.println("EditorId entered doesnot exist, Please re-enter");
                                System.out.println("Do you want to reenter the EditorID? 1.Yes 0.NO");
                                check3 = sc.nextInt();
                                sc.nextLine();
                                if (check3 == 0) {
                                    loopcontrol = 0;
                                    loopcontrol1 = 0;
                                    break;
                                }
                            }
                        }
                        //entering into the publication
                        System.out.println("Enter the publication id to which the editor is to be assigned");
                        pubId = sc.nextInt();
                        sc.nextLine();
                        query = "INSERT INTO EditorAssigned(staffID, pubID) VALUES(" + editorId + "," + pubId + ");";
                        dbManager.executeUpdate(query);
                        System.out.println("The editor was successfully assigned to the publication");
                        break;




                        //deleting editor from publication
                    case 11:
                        loopcontrol = 0;
                        editorId = 0;
                        //loop to check if editor id is present
                        while (loopcontrol == 0) {
                            System.out.println("Enter the editor id you want to delete");
                            editorId = sc.nextInt();
                            sc.nextLine();
                            query = "select staffID from Editor";
                            rs = dbManager.executeQuery(query);
                            flag = 0;
                            while (rs.next()) {
                                if (rs.getInt("StaffID") == editorId) {
                                    flag = 1;
                                    loopcontrol = 1;
                                }
                            }
                            //to check if editor id is present
                            if (flag != 1) {
                                System.out.println("EditorId entered doesnot exist, Please re-enter");
                                System.out.println("Do you want to reenter the EditorID? 1.Yes 0.NO");
                                check3 = sc.nextInt();
                                sc.nextLine();
                                if (check3 == 0) {
                                    loopcontrol = 0;
                                    loopcontrol1 = 0;
                                    break;
                                }
                            }
                        }
                        //Enter the publication
                        System.out.println("Enter the publication id to which the editor is to be removed from");
                        pubId = sc.nextInt();
                        sc.nextLine();
                        query = "DELETE FROM EditorAssigned WHERE pubId=" + pubId +
                            " and staffID=" + editorId + ";";
                        dbManager.executeUpdate(query);
                        System.out.println("the editor was successfully deleted from publication");

                        break;

                        //view assigned publication
                    case 12:
                        id = 0;
                        loopcontrol = 0;
                        //loop to check if values of editor is present
                        while (loopcontrol == 0) {
                            System.out.println("Enter the editor id you want to view publications for");
                            id = sc.nextInt();
                            sc.nextLine();
                            query = "select staffID from Editor";
                            rs = dbManager.executeQuery(query);
                            flag = 0;
                            while (rs.next()) {
                                if (rs.getInt("StaffID") == id) {
                                    flag = 1;
                                    loopcontrol = 1;
                                }
                            }
                            if (flag != 1) {
                                System.out.println("EditorId entered doesnot exist, Please re-enter");
                                System.out.println("Do you want to reenter the EditorID? 1.Yes 0.NO");
                                check3 = sc.nextInt();
                                sc.nextLine();
                                if (check3 == 0) {
                                    loopcontrol = 0;
                                    loopcontrol1 = 0;
                                    break;
                                }
                            }
                        }
                        //query to insert into editor assigned
                        query = "select pubID,title from Publication where pubID IN (SELECT pubId FROM EditorAssigned WHERE staffID=" + id + ");";
                        rs = dbManager.executeQuery(query);
                        if (rs.next() == false) {

                            System.out.println("ResultSet is empty");
                        } else {
                            do {
                                System.out.println("The assigned publication for staff id:" + id + " is:  " + rs.getInt("pubId") + "  with publication title: " + rs.getString("title")+"\n");
                            }
                            while (rs.next());
                        }
                        break;



                        //View tableofcontent - issue of magazine or journal for a publication
                    case 13:
                        System.out.println("Enter the publication id");
                        pubId = sc.nextInt();
                        sc.nextLine();
                        System.out.println("Enter the issue id of publication");
                        issueId = sc.nextInt();
                        sc.nextLine();
                        //selects the tabofCont
                        query = "SELECT tabOfCont FROM Issue WHERE pubID=" + pubId + " and issueID=" + issueId + ";";
                        rs = dbManager.executeQuery(query);
                        if (rs.next() == false) {
                            System.out.println("The publication id and issueId have are not valid or have no table of content");
                        } else {
                            do {
                                System.out.println("The table of content for issue of a publication id=" + pubId + " is " + rs.getString("tabOfCont"));
                            }
                            while (rs.next());
                        }
                        break;

                        /* 
                               //View tableofcontent - issue of journal of a publication
                              case 14:
                              System.out.println("Enter the publication id");
                              pubId = sc.nextInt();
                        	  sc.nextLine();
                              System.out.println("Enter the issue id of publication");
                              issueId = sc.nextInt();
                        	  sc.nextLine();
                              query = "SELECT tabOfCont FROM Issue WHERE pubID="+pubId+" and issueID="+issueId+";";
                              rs = dbManager.executeQuery(query);
                        	  while(rs.next())
                        	   {
                        	  System.out.println("The table of content for issue of journal of a publication id = " +pubId + " is "+rs.getString("tabOfCont"));
                        	   } */




                        //View tableofcontent - edition of book for a publication :
                    case 14:
                        System.out.println("Enter the publication id");
                        pubId = sc.nextInt();
                        sc.nextLine();
                        System.out.println("Enter the edition id of publication");
                        editionId = sc.nextInt();
                        sc.nextLine();
                        //select the tabOfCont
                        query = "SELECT tabOfCont FROM Edition WHERE pubID=" + pubId + " and editionID=" + editionId + ";";
                        rs = dbManager.executeQuery(query);
                        //results returning 
                        if (rs.next() == false) {
                            System.out.println("The publication id and issueId have are not valid or have no table of content");
                        } else {
                            do {
                                System.out.println("The table of content for edition of book of a publication id =" + pubId + " is " + rs.getString("tabOfCont"));
                            }
                            while (rs.next());
                        }
                        break;



                        //Update tableofcontent - issue of magazine+journal for a publication :
                    case 15:
                        System.out.println("Do you want to enter the table of content manally or automatically based on the articles and their titles for the publication? 1.manual 0.automatic");
                        check = sc.nextInt();
                        sc.nextLine();
                        //updating the table manually for the tabofCont
                        if (check == 1) {
                            System.out.println("Enter the publication id");
                            pubId = sc.nextInt();
                            sc.nextLine();
                            System.out.println("Enter the issue id of publication");
                            issueId = sc.nextInt();
                            sc.nextLine();
                            System.out.println("Enter the table of content details");
                            String tabofCont = sc.nextLine();
                            query = "UPDATE Issue SET tabOfCont='" + tabofCont + "'WHERE pubID=" + pubId + " and issueID=" + issueId + ";";
                            String query6 = "Select tabOfCont from Issue where pubID =" + pubId + " and issueID=" + issueId + ";";
                            rs = dbManager.executeQuery(query6);
                            if (rs.next() == false) {
                                System.out.println("The publication id and editionID have are not valid or have no table of content");
                                break;
                            }
                            dbManager.executeUpdate(query);
                            System.out.println("the table of content was successfully updated for issue of a publication");
                        }
                        //updating the table automatically for the tabofCont
                        if (check == 0) {
                            System.out.println("Enter the publication id");
                            pubId = sc.nextInt();
                            sc.nextLine();
                            System.out.println("Enter the issue id of publication");
                            issueId = sc.nextInt();
                            sc.nextLine();
                            String query6 = "Select tabOfCont from Issue where pubID =" + pubId + " and issueID=" + issueId + ";";
                            rs = dbManager.executeQuery(query6);
                            if (rs.next() == false) {
                                System.out.println("The publication id and editionID have are not valid or have no table of content");
                                break;
                            }
							               //query to get artNUm and title accordingly
                            query6 = "select artNum,title from IssueContains C NATURAL JOIN Issue I INNER JOIN Article A ON C.artID=A.artID WHERE pubID=" + pubId + " and issueID=" + issueId + ";";
                            String creator = "";
                            rs = dbManager.executeQuery(query6);
                            while (rs.next()) {
                                creator = creator + "ArticleNO.";
                                creator = creator + rs.getInt("artNum");
                                creator = creator + "\n";
                                creator = creator + "title : ";
                                creator = creator + rs.getString("title");
                                creator = creator + "\n";
                            }
                            //System.out.println(creator);
                            String query7 = "UPDATE Issue SET tabOfCont='" + creator + "'WHERE pubID=" + pubId + " and issueID=" + issueId + ";";
                            dbManager.executeUpdate(query7);
                            System.out.println("the table of content was successfully updated for issue of a publication");
                        }
                        break;



                        /* //Update tableofcontent - issue of journal for a publication :
                              case 16:
                              System.out.println("Enter the publication id");
                              pubId = sc.nextInt();
                        	  sc.nextLine();
                              System.out.println("Enter the issue id of publication");
                              issueId = sc.nextInt();
                        	  sc.nextLine();
                              System.out.println("Enter the table of content details");
                              tabofCont = sc.nextLine();
                              query = "UPDATE Issue SET tabOfCont='"+tabofCont+"'WHERE pubID="+pubId+" and issueID="+issueId+";";
                              dbManager.executeUpdate(query);
                        	  System.out.println("the table of content was successfully updated for issue of journal for a publication");
                              break; */


                        //Update tableofcontent - edition of book for a publication :
                    case 16:
                        System.out.println("Do you want to enter the table of content manally or automatically based on the chpaters and their titles for the publication? 1.manual 0.automatic");
                        check = sc.nextInt();
                        sc.nextLine();



                        //entering the tabofCont manually
                        if (check == 1) {
                            System.out.println("Enter the publication id");
                            pubId = sc.nextInt();
                            sc.nextLine();
                            System.out.println("Enter the edition id of publication");
                            int editionid = sc.nextInt();
                            sc.nextLine();
                            System.out.println("Enter the table of content details");
                            String tabofCont1 = sc.nextLine();
                            String query5 = "Select tabOfCont from Edition where pubID =" + pubId + " and editionID=" + editionid + ";";
                            rs = dbManager.executeQuery(query5);
                            if (rs.next() == false) {
                                System.out.println("The publication id and editionID have are not valid or have no table of content");
                                break;
                            }
                            query = "UPDATE Edition SET tabOfCont='" + tabofCont1 + "' WHERE pubID=" + pubId + " and editionID=" + editionid + ";";
                            dbManager.executeUpdate(query);
                            System.out.println("the table of content was successfully updated for edition of book for a publication");
                        }


                        //entering the tabofCont automatically
                        if (check == 0) {
                            System.out.println("Enter the publication id");
                            pubId = sc.nextInt();
                            sc.nextLine();
                            System.out.println("Enter the edition id of publication");
                            int editionid = sc.nextInt();
                            sc.nextLine();
                            String query6 = "Select tabOfCont from Edition where pubID =" + pubId + " and editionID=" + editionid + ";";
                            rs = dbManager.executeQuery(query6);
                            if (rs.next() == false) {
                                System.out.println("The publication id and editionID have are not valid or have no table of content");
                                break;
                            }
                            query6 = "select chapNum,title from EditionContains C NATURAL JOIN Edition I INNER JOIN Chapter A ON C.chapId=A.chapId WHERE pubID=" + pubId + " and editionID=" + editionid + ";";
                            String creator = "";
                            rs = dbManager.executeQuery(query6);
                            //appending the sring builder accordingly
                            while (rs.next()) {
                                creator = creator + "ChapterNO.";
                                creator = creator + rs.getInt("chapNum");
                                creator = creator + "\n";
                                creator = creator + "title : ";
                                creator = creator + rs.getString("title");
                                creator = creator + "\n";
                            }
                            //System.out.println(creator);
                            String query7 = "Select tabOfCont from Edition where pubID =" + pubId + " and editionID=" + editionid + ";";
                            dbManager.executeUpdate(query7);
                            System.out.println("the table of content was successfully updated for edition of a publication");
                        }
                        break;


                        //searching the respective databases
                    case 17:
                        System.out.println("Enter which table you want to search for");
                        System.out.println("1.Publication");
                        System.out.println("2.Books");
                        System.out.println("3.Edition");
                        System.out.println("4.Magazines");
                        System.out.println("5.Journals");
                        System.out.println("6.Articles");
                        System.out.println("7.Chapter");
                        System.out.println("8.Issue");
                        System.out.println("9.IssueContains");
                        System.out.println("10.ChapterContains");
                        chosenval = sc.nextInt();
                        sc.nextLine();
                        //switching on chosen values
                        switch (chosenval) {
                            //search publication
                            case 1:
                                query = "select * from Publication";
                                rs = dbManager.executeQuery(query);
                                while (rs.next()) {
                                    System.out.println("pubid=" + rs.getInt("pubID"));
                                    System.out.println("title=" + rs.getString("title"));
                                    System.out.println("");
                                }

                                break;

                                //search books
                            case 2:
                                query = "select * from Books";
                                rs = dbManager.executeQuery(query);
                                while (rs.next()) {
                                    System.out.println("pubid=" + rs.getInt("pubID"));
                                    System.out.println("topic=" + rs.getString("topic"));
                                    System.out.println("");
                                }

                                break;

                                //search edition
                            case 3:
                                query = "select * from Edition";
                                rs = dbManager.executeQuery(query);
                                while (rs.next()) {
                                    System.out.println("editionid=" + rs.getInt("editionID"));
                                    System.out.println("pubid=" + rs.getInt("pubID"));
                                    System.out.println("isbn=" + rs.getString("ISBN"));
                                    System.out.println("date=" + rs.getString("date"));
                                    System.out.println("tableofcontent=" + rs.getString("tabOfCont"));
                                }

                                break;


                                //search magazine
                            case 4:
                                query = "select * from Magazines";
                                rs = dbManager.executeQuery(query);
                                while (rs.next()) {
                                    System.out.println("pubid=" + rs.getInt("pubID"));
                                    System.out.println("periodicity=" + rs.getString("periodicity"));
                                    System.out.println("");

                                }
                                break;


                                //journals
                            case 5:
                                query = "select * from Journals";
                                rs = dbManager.executeQuery(query);
                                while (rs.next()) {
                                    System.out.println("pubid=" + rs.getInt("pubID"));
                                    System.out.println("periodicity=" + rs.getString("periodicity"));
                                    System.out.println("");

                                }
                                break;

                                //search article
                            case 6:
                                query = "select * from Article";
                                rs = dbManager.executeQuery(query);
                                while (rs.next()) {
                                    System.out.println("artid=" + rs.getInt("artID"));
                                    System.out.println("topic=" + rs.getString("topic"));
                                    System.out.println("title=" + rs.getString("title"));
                                    System.out.println("date=" + rs.getString("date"));
                                    System.out.println("text=" + rs.getString("text"));
                                    System.out.println("");
                                }
                                break;

                                //search chapter
                            case 7:
                                query = "select * from Chapter";
                                rs = dbManager.executeQuery(query);
                                while (rs.next()) {
                                    System.out.println("chapId=" + rs.getInt("chapID"));
                                    System.out.println("title=" + rs.getString("title"));
                                    System.out.println("date=" + rs.getString("date"));
                                    System.out.println("text=" + rs.getString("text"));
                                    System.out.println("");
                                }

                                break;


                                //search issue
                            case 8:
                                query = "select * from Issue";
                                rs = dbManager.executeQuery(query);
                                while (rs.next()) {
                                    System.out.println("issueId=" + rs.getInt("issueID"));
                                    System.out.println("pubId=" + rs.getInt("pubID"));
                                    System.out.println("date=" + rs.getString("date"));
                                    System.out.println("tabOfCont=" + rs.getString("tabOfCont"));
                                    System.out.println("");
                                }
                                break;

                                //search issuecontains
                            case 9:
                                query = "select * from IssueContains";
                                rs = dbManager.executeQuery(query);
                                while (rs.next()) {
                                    System.out.println("issueId=" + rs.getInt("issueID"));
                                    System.out.println("pubId=" + rs.getInt("pubID"));
                                    System.out.println("artid=" + rs.getString("artID"));
                                    System.out.println("artnum=" + rs.getString("artNum"));
                                    System.out.println("");
                                }
                                break;


                                //search editionContains
                            case 10:
                                query = "select * from EditionContains";
                                rs = dbManager.executeQuery(query);
                                while (rs.next()) {
                                    System.out.println("editionID=" + rs.getInt("editionID"));
                                    System.out.println("pubId=" + rs.getInt("pubID"));
                                    System.out.println("chapID=" + rs.getString("chapID"));
                                    System.out.println("chapNum=" + rs.getString("chapNum"));
                                    System.out.println("");
                                }
                                break;


                            default:
                                break;
                        }
                        break;

                    default:
                        System.out.println("Invalid Choice");
                        break;
                }
                System.out.println("Would you like to perfrom another editing or publishing operation? enter 1 for Yes or 0 for N");
                flag15 = sc.nextInt();
                sc.nextLine();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error in excuting the sql statement");
            }
        }



  }

  public static void production(DBManager dbManager, Scanner sc){
    //switch case operations
	boolean flag = true;
	while(flag){
    System.out.println("Determine what Production activity you want to perform:");
    System.out.println("1. Add a new Edition in DB");
    System.out.println("2. Update Edition of a Book in DB");
    System.out.println("3. Delete an Edition from DB");
    System.out.println("4. Add a new Chapter to an Edition in DB");
	  System.out.println("5. Update a Chapter in DB");
    System.out.println("6. Add (append) Text to a Chapter in DB");
	  System.out.println("7. Update Text of a Chapter in DB"); 
	  System.out.println("8. Delete a Chapter from DB");
    System.out.println("9. Assign Author to edition");  
    System.out.println("10. Find Books by Topic in DB");
	  System.out.println("11. Find Books by Date in DB");
	  System.out.println("12. Find Books by Author Name in DB");
    System.out.println("13. Add a new Issue in DB");
	  System.out.println("14. Update an Issue in DB");
	  System.out.println("15. Delete an Issue from DB");
    System.out.println("16. Add new Article to an Issue in DB");
	  System.out.println("17. Update an Article in DB");
    System.out.println("18. Add (append) Text to an Article in DB");
	  System.out.println("19. Update Text to an Article in DB"); 
	  System.out.println("20. Delete an Article from DB");
    System.out.println("21. Assign Author to article"); 
	  System.out.println("22. Find Articles by Topic in DB");
    System.out.println("23. Find Articles by Date in DB");
    System.out.println("24. Find Articles by Author Name in DB");
    System.out.println("25. Enter Payment Info in DB"); 
	  System.out.println("26. Find Payment by Pay ID in DB");
	  System.out.println("27. Find Payment by Claimed Status in DB");
  
	  int choice =sc.nextInt();
	  sc.nextLine();
    String query1 = "";
	  String query2 = "";
    String query3 = ""; 
    ResultSet rs;
    String chapName, chapText, artName, artText, isbn, tabOfCont, artTopic, artTitle, chapTitle, topic, date, startDate, endDate, name, text, generatedDate, claimedStatus;
    int artNum, chapNum, editionId, chapId, issueId, artId, pubId, amount, staffId, payId; 
    int updateCount;
    boolean checkFlag = false;
	  switch(choice){
     
     
     
    //Add new Edition
		case 1:
    System.out.println("1. Add a new Edition in DB:");
    checkFlag = false;
    try{
      System.out.println("Enter Publication ID (Book) for which you wish to add new Edition:");
  		pubId = sc.nextInt();
  		sc.nextLine();
      
      System.out.println("Enter Edition ID for the Edition:");
  		editionId = sc.nextInt();
  		sc.nextLine();
  		System.out.println("Enter ISBN for the Edition:");
  		isbn = sc.nextLine();
  		System.out.println("If you would like to enter table of Contents to this Edition, please type below. Press Enter to skip:");
    	tabOfCont = sc.nextLine();
      if(tabOfCont.equals(""))
        tabOfCont = "NULL";
      else 
        tabOfCont = "\"" + tabOfCont + "\"";    
      System.out.println("To enter date to this Edition, please type below [YYYY-MM-DD]. Press Enter for today's date:");
    	date = sc.nextLine();
      if(!checkValidDate(date)){
                    if(date.equals("")){
      date = LocalDate.now().toString();
     }
     else{
             System.out.println("Invalid Date Format");
             break;
                }
      }
      date = "\"" + date + "\"";
    }
    catch(InputMismatchException e){
      e.printStackTrace();
      System.out.println("Input type error!");
      sc.nextLine();
      break;
    }
		query2 = "INSERT INTO Edition (editionId, pubID, ISBN, date, tabOfCont) VALUES ("+editionId+","+ pubId +", \""+ isbn+"\","+date+","+tabOfCont+");";
		try{
     query1 = "SELECT * FROM Books WHERE pubID = "+pubId+";";
      rs = dbManager.executeQuery(query1);
      while(rs.next()){
      checkFlag = true;
      }
      if(checkFlag){
      dbManager.executeUpdate(query2);
      System.out.println("Successfully added Edition to a publication in DB");
    }
    else
     System.out.println("Entered publication ID is not a Book type!");
    }
    catch(Exception e){
      e.printStackTrace();
      System.out.println("Error adding Edition to a publication in DB!");
    }
    break;
	
    //Update Edition	
		case 2:
    System.out.println("2. Update Edition of a Book in DB:");
    try{
    System.out.println("Enter a Publication ID whose Edition you wish to update:");
		pubId = sc.nextInt();
		sc.nextLine();
    System.out.println("Enter Edition ID which you want to update:");
		editionId = sc.nextInt();
		sc.nextLine();
		
        System.out.println("Which parameter do you want to update:");
        System.out.println("1. Edition ID");
        System.out.println("2. ISBN number");
        System.out.println("3. Date");
        System.out.println("4. Table of Contents");
        
        int ch = sc.nextInt();
        sc.nextLine();
       query1="";
        switch(ch){
          case 1:
          System.out.println("Enter updated Edition ID: ");
          int newEditionId = sc.nextInt();
          sc.nextLine();
          query1 = "UPDATE Edition SET editionID = \""+newEditionId+"\" WHERE editionID = "+ editionId+" AND pubID = "+pubId+";";
          break;
          case 2:
          System.out.println("Enter updated ISBN: ");
          isbn = sc.nextLine();
          query1 = "UPDATE Edition SET ISBN = \""+isbn+"\" WHERE editionID = "+ editionId+" AND pubID = "+pubId+";";
          break;
          case 3:
          System.out.println("Enter updated Date: ");
          date = sc.nextLine();
          if(!checkValidDate(date)){
                System.out.println("Invalid Date Format.");
                query1 = "";
                break;
          }
          date = "\"" + date + "\"";
          query1 = "UPDATE Edition SET date = "+date+" WHERE editionID = "+ editionId+" AND pubID = "+pubId+";";
          break;
          case 4:
          System.out.println("Enter updated table of Contents: ");
          tabOfCont = sc.nextLine();
          query1 = "UPDATE Edition SET tabOfCont = \""+tabOfCont+"\" WHERE editionID = "+ editionId+" AND pubID = "+pubId+";";
          break;
          default:
          break;
        }
      }
      catch(InputMismatchException e){
        e.printStackTrace();
        System.out.println("Input type error!");
        sc.nextLine();
        break;
    }  
      if(!query1.equals("")) {
		  try{
          
          updateCount = dbManager.executeUpdate(query1);
          if(updateCount>0)
            System.out.println("Successfully updated Edition in DB");
          else 
            System.out.println("No matching records found in DB!");  
        }
        catch(Exception e){
          e.printStackTrace();
          System.out.println("Error updating Edition in DB!");
        }
      }
      break; 


	//Delete Edition	
    
		case 3:
    System.out.println("3. Delete an Edition from DB:");	
    try{
		System.out.println("Enter edition ID of the Edition you wish to delete:");
		editionId = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter Pub ID of which this is an Edition:");
		pubId = sc.nextInt();
		sc.nextLine();
		query1 =  "DELETE FROM Edition WHERE editionId = "+editionId+" AND pubID = "+pubId+";";
    }
      catch(InputMismatchException e){
        e.printStackTrace();
        System.out.println("Input type error!");
        sc.nextLine();
        break;
    }
		try{
          
       updateCount = dbManager.executeUpdate(query1);
		   if(updateCount>0)
         System.out.println("Successfully deleted Edition from DB");
       else 
        System.out.println("No matching records found in DB!");    
        }
        catch(Exception e){
          e.printStackTrace();
          System.out.println("Error deleting edition from DB!");
          
        }
        break;
        
    //Add Chapter	
    case 4: 
    System.out.println("4. Add a new Chapter to an Edition in DB:");
    checkFlag = false;
    try{
    System.out.println("Enter Chapter ID for the Chapter:");
    chapId = sc.nextInt();
    sc.nextLine();
    System.out.println("Enter Chapter number for the Chapter in the Edition:");
    chapNum = sc.nextInt();
    sc.nextLine();
		System.out.println("Enter title for Chapter:");
		chapTitle = sc.nextLine();
		System.out.println("If you would like to enter text to this Chapter please type below. Press Enter to skip:");
    chapText = sc.nextLine();
    System.out.println("If you would like to enter date for this Chapter, please type below [YYYY-MM-DD]. Press Enter to skip:");
  	date = sc.nextLine();
    if(date.equals(""))
      date = "NULL";
     else{ 
    if(!checkValidDate(date)){
                System.out.println("Invalid Date Format.");
                break;
    }
    else
      date = "\"" + date + "\"";
     }
    System.out.println("Enter publication ID of the Edition to which this Chapter belongs:");
		pubId = sc.nextInt();
		sc.nextLine();
    
		System.out.println("Enter Edition ID to which you want to assign this Chapter:");
		editionId = sc.nextInt();

		sc.nextLine();
   }
      catch(InputMismatchException e){
        e.printStackTrace();
        System.out.println("Input type error!");
        sc.nextLine();
        break;
    }
		query2 = "INSERT INTO Chapter(chapID, title, date, text) VALUES ("+chapId+",\""+chapTitle+"\", "+date+",\""+chapText+"\");";
		
		query3 = "INSERT INTO EditionContains(pubID, editionID, chapID, chapNum) VALUES ("+pubId+","+editionId+","+chapId+","+chapNum+");";

		try{
    
          dbManager.startTransaction();  
          query1 = "SELECT * FROM Books WHERE pubID = "+pubId+";";
    rs = dbManager.executeQuery(query1);
      while(rs.next()){
      checkFlag = true;
      }
      if(checkFlag){
          dbManager.executeUpdate(query2);
		  dbManager.executeUpdate(query3);
          dbManager.commitTransaction();
          System.out.println("Successfully added Chapter in DB");
          }
          else
           System.out.println("Entered publication ID is not a Book type!");
        }
        
        catch(Exception e){
		  e.printStackTrace();
          System.out.println("Error adding Chapter in DB!");
          try{
            dbManager.rollbackTransaction();
          }
          catch(Exception ex){
            System.out.println("Error rolling back. Connection to DB lost.");
          }
        }
        break;
		
	//Update Chapter	
		case 5:
    System.out.println("5. Update a Chapter in DB:");
    try{
		System.out.println("Enter a chapter ID of the Chapter you wish to update:");
		chapId = sc.nextInt();
		sc.nextLine();
   
    System.out.println("Which parameter do you want to update:");
        System.out.println("1. Chapter ID");
        System.out.println("2. Title");
        System.out.println("3. Date");
        System.out.println("4. Text");
        
        int ch = sc.nextInt();
        sc.nextLine();
       query1="";
       switch(ch){
          case 1:
          System.out.println("Enter updated Chapter ID: ");
          int newChapId = sc.nextInt();
          sc.nextLine();
          query1 = "UPDATE Chapter SET chapID = \""+ newChapId+"\" WHERE chapID = "+chapId+";";
          break;
          case 2:
          System.out.println("Enter updated Title: ");
          chapTitle = sc.nextLine();
          query1 = "UPDATE Chapter SET title = \""+ chapTitle+"\" WHERE chapID = "+chapId+";";
          break;
          case 3:
          System.out.println("Enter updated Date: ");
          date = sc.nextLine();
          if(!checkValidDate(date)){
                System.out.println("Invalid Date Format.");
                query1 = "";
                break;
          }
          date = "\"" + date + "\"";
          query1 = "UPDATE Chapter SET date = "+date+" WHERE chapID = "+chapId+";";
          break;
          case 4:
          System.out.println("Enter updated Text: ");
          chapText = sc.nextLine();
          query1 = "UPDATE Chapter SET text = \""+ chapText+"\" WHERE chapID = "+chapId+";";
          break;
          default:
          break;
        }
     }
      catch(InputMismatchException e){
        e.printStackTrace();
        System.out.println("Input type error!");
        sc.nextLine();
        break;
    }  
		try{
          updateCount = dbManager.executeUpdate(query1);
         if(updateCount>0)
           System.out.println("Successfully updated chapter in DB");
         else 
          System.out.println("No matching records found in DB!");   
        }
        catch(Exception e){
		  e.printStackTrace();
          System.out.println("Error updating chapter in DB!");        
        }
        break;
        
        //Add Chapter Text
    case 6:
    text = "";
    System.out.println("6. Add (append) Text to a Chapter in DB:");
    try{
      System.out.println("Enter chapter ID for the Chapter:");
  		chapId = sc.nextInt();
      sc.nextLine();
      System.out.println("To append text to this Chapter, please type below:");
  		chapText = sc.nextLine();
    }
    catch(InputMismatchException e){
        e.printStackTrace();
        System.out.println("Input type error!");
        sc.nextLine();
        break;
    }  
		query1 = "SELECT text FROM Chapter WHERE chapID = "+chapId+";";

		try{
        dbManager.startTransaction();
        rs = dbManager.executeQuery(query1);
        while(rs.next()){       
          text = rs.getString("text");
        }
        chapText = text + " " + chapText;
        query2 = "UPDATE Chapter SET text = \"" + chapText + "\" WHERE chapID = "+chapId+";";
  		  updateCount = dbManager.executeUpdate(query2);
        dbManager.commitTransaction();
        if(updateCount>0)
          System.out.println("Successfully added text to the chapter in DB");
        else 
          System.out.println("No matching records found in DB!"); 
    }
    catch(Exception e){
        e.printStackTrace();
        System.out.println("Error adding text to a chapter in DB!");
        try{
          dbManager.rollbackTransaction();
        }
        catch(Exception ex){
          System.out.println("Error rolling back. Connection to DB lost.");
        }
    }
    break;
		
	  //Update Chapter text	
		case 7:
    System.out.println("7. Update Text of a Chapter in DB:");
    try{
      System.out.println("Enter a Chapter ID of the chapter to update Text:");
  		chapId = sc.nextInt();
  		sc.nextLine();
  		System.out.println("Enter text to be updated to this Chapter:");
  		chapText = sc.nextLine();
		}
    catch(InputMismatchException e){
      e.printStackTrace();
      System.out.println("Input type error!");
      sc.nextLine();
      break;
    }
		query1 =  "UPDATE Chapter SET text = \"" + chapText + "\" WHERE chapID = " + chapId+ ";";
		
		try{   
      updateCount = dbManager.executeUpdate(query1); 
      if(updateCount>0)
        System.out.println("Successfully updated chapter text in DB");
      else 
        System.out.println("No matching records found in DB!");
    }
    catch(Exception e){
      e.printStackTrace();
      System.out.println("Error updating chapter text in DB!");     
    }
    break;

	//Delete Chapter		
		case 8:
   System.out.println("8. Delete a Chapter from DB:");
    try{
		System.out.println("Enter chapter ID of the chapter you wish to delete:");
		chapId = sc.nextInt();
		sc.nextLine();
    }
      catch(InputMismatchException e){
        e.printStackTrace();
        System.out.println("Input type error!");
        sc.nextLine();
        break;
    }  
		query1 =  "DELETE FROM Chapter WHERE chapID = "+chapId+";";
		
		try{
        
          updateCount = dbManager.executeUpdate(query1);
          if(updateCount>0)
           System.out.println("Successfully deleted chapter from DB");
            else 
          System.out.println("No matching records found in DB!");
        }
        catch(Exception e){
		  e.printStackTrace();
          System.out.println("Error deleted chapter from DB!");
        }
        break;    
        
   
	 //Author assigned to Edition	
		case 9:
    System.out.println("9. Assign Author to edition:");
    query1 = "";
    //Take user input for Publication ID, Edition ID and Author ID;
		try{
        System.out.println("Enter Publication Id (Book Id) for whose Edition you wish to assign an author:");
    		pubId = sc.nextInt();
    		sc.nextLine();
    		System.out.println("Enter Edition Id of the Edition of the Book:");
    		editionId = sc.nextInt();
    		sc.nextLine();
    		System.out.println("Enter Staff Id (Author Id) of the Author:");
    		staffId = sc.nextInt();
    		sc.nextLine();
        //Generate Query
    		query1 = "INSERT INTO AuthorAssignedEdition (staffID, pubID, editionID) VALUES ("+staffId+"," +pubId+","+editionId+");";
		}
   //Catch input type mismatch exception
    catch(InputMismatchException e){
        e.printStackTrace();
        System.out.println("Input type error!");
        sc.nextLine();
        query1="";
        break;
    }
    
   if(!query1.equals("")){
   //Try to execute update database query
		    try{
            dbManager.executeUpdate(query1);
            System.out.println("Successfully assigned author to Edition in DB");
        }
        catch(Exception e){
		        e.printStackTrace();
            System.out.println("Error assiging author to Edition in DB!");
        }
    }    
    break;     
        
    //Find books by topic	
		case 10:
    System.out.println("10. Find Books by Topic in DB:");
		System.out.println("Enter a topic to find Books:");
		topic = sc.nextLine();
		query1 = "SELECT Publication.pubID, title FROM Publication INNER JOIN Books ON Publication.pubID = Books.pubID WHERE topic = \""+ topic+"\";";
		
		try{
         
       rs = dbManager.executeQuery(query1);
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
          System.out.println("Error finding books by given topic in DB!");

        }
        break;
	
	
	//Find books by date	
		case 11:	  
    System.out.println("11. Find Books by Date in DB:");
    System.out.println("Enter a Start Date [YYYY-MM-DD] to find Books (Press Enter for today's date):"); 
		startDate = sc.nextLine();
		if(!checkValidDate(startDate)){
     if(startDate.equals("")){
     
      startDate = LocalDate.now().toString();
     }
     else{
             System.out.println("Invalid Date Format");
             break;
                }
     }     		
    System.out.println("Enter End Date [YYYY-MM-DD] to find Books (Press Enter for today's date):"); 
		endDate = sc.nextLine();
		if(!checkValidDate(endDate)){
     if(endDate.equals("")){
      endDate = LocalDate.now().toString();
     }
     else{
             System.out.println("Invalid Date Format");
             break;
                }
     }    
		query1 = "SELECT Publication.pubID, title FROM Publication INNER JOIN Edition ON Publication.pubID = Edition.pubID WHERE date IS NOT NULL AND date BETWEEN \""+ startDate+"\" AND  \""+ endDate+"\";";
		
	try{
         
        rs = dbManager.executeQuery(query1);
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
         System.out.println("Error finding books by given date in DB!");

        }

        break;
		
	//Find books by author name	
		case 12:
   	System.out.println("12. Find Books by Author Name in DB:");
		System.out.println("Enter Author name to find Books:");
		name = sc.nextLine();
		query1 = "SELECT Publication.pubID, title FROM Publication INNER JOIN AuthorAssignedEdition ON Publication.pubID = AuthorAssignedEdition.pubID WHERE staffID IN (SELECT staffID FROM Staff WHERE username = \"" + name+ "\");";
		
		try{
      
           rs = dbManager.executeQuery(query1);
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
          System.out.println("Error finding books by given Author name in DB!");
        }
        break;
	

//Add Issue	
		case 13:
    System.out.println("13. Add a new Issue in DB:");
    checkFlag = false;
    try{
  		System.out.println("Enter Publication ID (Journal or Magazine) for which you wish to add Issue:");
  		pubId = sc.nextInt();
  		sc.nextLine();
      System.out.println("Enter Issue ID for the Issue:");
  		issueId = sc.nextInt();
  		sc.nextLine();
  		System.out.println("If you would like to enter table of Contents to this Issue, please type below. Press Enter to skip:");
    	tabOfCont = sc.nextLine();
      if(tabOfCont.equals(""))
         tabOfCont = "NULL";
      else 
        tabOfCont = "\"" + tabOfCont + "\"";    
      System.out.println("To enter date to this Issue, please type below [YYYY-MM-DD]. Press Enter for today's date:");
    	date = sc.nextLine();
      if(!checkValidDate(date)){
                  if(date.equals("")){
      date = LocalDate.now().toString();
     }
     else{
             System.out.println("Invalid Date Format");
             break;
                }
      }
      date = "\"" + date + "\"";
		}
    catch(InputMismatchException e){
      e.printStackTrace();
      System.out.println("Input type error!");
      sc.nextLine();
      break;
    }
		query2 = "INSERT INTO Issue (issueID, pubID, date,tabOfCont) VALUES ("+issueId+","+pubId+","+date+","+tabOfCont+");";
		try{
   query1 = "SELECT * FROM Journals WHERE pubID = "+pubId+" UNION SELECT * FROM Magazines WHERE pubID = "+pubId+";";
      rs = dbManager.executeQuery(query1);
      while(rs.next()){
      checkFlag = true;
      }
      if(checkFlag){
      dbManager.executeUpdate(query2);
     System.out.println("Successfully added Issue to a publication in DB");
    }
    else
     System.out.println("Entered publication ID is not a Journal or Magazine type!");
    }
    catch(Exception e){
      e.printStackTrace();
      System.out.println("Error adding Issue to a publication in DB!");
    }
    break;
	
	//Update Issue	
		case 14:
    System.out.println("14. Update an Issue in DB:");
    try{
    System.out.println("Enter a Publication ID whose Issue you wish to update:");
		pubId = sc.nextInt();
		sc.nextLine();
    System.out.println("Enter Issue ID which you want to update:");
		issueId = sc.nextInt();
		sc.nextLine();
		
        System.out.println("Which parameter do you want to update:");
        System.out.println("1. Issue ID");
        System.out.println("2. Date");
        System.out.println("3. Table of Contents");
        
        int ch = sc.nextInt();
        sc.nextLine();
       query1="";
        switch(ch){
          case 1:
          System.out.println("Enter updated Issue ID: ");
          int newIssueId = sc.nextInt();
          sc.nextLine();
          query1 = "UPDATE Issue SET issueID = \""+newIssueId+"\" WHERE issueID = "+ issueId+" AND pubID = "+pubId+";";
          break;
          
          case 2:
          System.out.println("Enter updated Date: ");
          date = sc.nextLine();
          if(!checkValidDate(date)){
                System.out.println("Invalid Date Format.");
                query1 = "";
                break;
          }
          date = "\"" + date + "\"";
          query1 = "UPDATE Issue SET date = "+date+" WHERE issueID = "+ issueId+" AND pubID = "+pubId+";";
          break;
          
          case 3:
          System.out.println("Enter updated table of Contents: ");
          tabOfCont = sc.nextLine();
          query1 = "UPDATE Issue SET tabOfCont = \""+tabOfCont+"\" WHERE issueID = "+ issueId+" AND pubID = "+pubId+";";
          break;
          default:
          break;
        }
      }
      catch(InputMismatchException e){
        e.printStackTrace();
        System.out.println("Input type error!");
        sc.nextLine();
        break;
    }
    
   if(!query1.equals("")){
		try{   
      updateCount = dbManager.executeUpdate(query1);
      if(updateCount>0)    
		    System.out.println("Successfully updated Issue in DB");
      else 
        System.out.println("No matching records found in DB!");  
        }
        catch(Exception e){
          e.printStackTrace();
          System.out.println("Error updating Issue in DB!");              
        }
        }
        break;
		
   
   //Delete Issue	
	case 15:
    System.out.println("15. Delete an Issue from DB:");
    try{
		System.out.println("Enter issue ID of the Issue you wish to delete:");
		issueId = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter Pub ID of which this is an Issue:");
		pubId = sc.nextInt();
		sc.nextLine();
		query1 =  "DELETE FROM Issue WHERE issueID ="+issueId+" AND pubID = "+pubId+";";
    }
      catch(InputMismatchException e){
        e.printStackTrace();
        System.out.println("Input type error!");
        sc.nextLine();
        break;
    }
		try{
          
        updateCount =  dbManager.executeUpdate(query1);
		    if(updateCount>0)
          System.out.println("Successfully deleted issue from DB");
        else 
          System.out.println("No matching records found in DB!");  
        }
        catch(Exception e){
          e.printStackTrace();
          System.out.println("Error deleted issue from DB!");      
        }
        break;
	
//Add Article to an Issue	
		case 16:
    System.out.println("16. Add new Article to an Issue in DB:");
    checkFlag = false;
    try{
    //Take user input for a new Article ID  
    System.out.println("Enter Article ID for the Article:");
    artId = sc.nextInt();
    sc.nextLine();
    //Take user input for a new Article number for the Article in the Issue
    System.out.println("Enter Article number for the Article in the Issue:");
    artNum = sc.nextInt();
    sc.nextLine();
    //Take user input for Article topic
		System.out.println("Enter topic for Article:");
		artTopic = sc.nextLine();
    //Take user input for Article title 
		System.out.println("Enter title for Article:");
		artTitle = sc.nextLine();
    //Take user input for Article text
    System.out.println("If you would like to enter text to this article please type below. Press Enter to skip:");
    artText = sc.nextLine();
    //Take user input for Article date
    System.out.println("If you would like to enter date for this Article, please type below [YYYY-MM-DD]. Press Enter to skip:");
  	date = sc.nextLine();
    if(date.equals(""))
      date = "NULL";
     else{ 
    if(!checkValidDate(date)){
                System.out.println("Invalid Date Format.");
                break;
    }
    else
      date = "\"" + date + "\"";
     }
	//Take user input for Publication ID of the Issue to which you want to assign the Article 
    System.out.println("Enter Publication ID of the Issue to which you want to assign this Article:");
    pubId = sc.nextInt();
    sc.nextLine();
  //Take user input for Issue ID of the above Publication
    System.out.println("Enter Issue ID of the Issue to which you want to assign this Article:");
     issueId = sc.nextInt();

		sc.nextLine();
    }
      catch(InputMismatchException e){
        e.printStackTrace();
        System.out.println("Input type error!");
        sc.nextLine();
        break;
    }
		query2 = "INSERT INTO Article(artID, topic, title, date, text) VALUES ("+artId+", \""+artTopic+"\", \""+artTitle+"\", "+date+", \""+artText+"\");";
		
		query3 = "INSERT INTO IssueContains(pubID, issueID, artID, artNum) VALUES ("+pubId+","+issueId+","+artId+","+artNum+");";
	
		try{
      //Begin transaction
          dbManager.startTransaction();
          query1 = "SELECT * FROM Journals WHERE pubID = "+pubId+" UNION SELECT * FROM Magazines WHERE pubID = "+pubId+";";
      rs = dbManager.executeQuery(query1);
      //Enable check flag to check if publication is of correct type(Journal/Magazine)
      while(rs.next()){
      checkFlag = true;
      }
      //Proceed execution based on check flag
      if(checkFlag){
          dbManager.executeUpdate(query2);
		  dbManager.executeUpdate(query3);
        dbManager.commitTransaction();
		  System.out.println("Successfully added Article in DB");
        }
    else
     System.out.println("Entered publication ID is not a Journal or Magazine type!");
        }
        catch(Exception e){
          e.printStackTrace();
          System.out.println("Error adding Article in DB!");
          try{
            dbManager.rollbackTransaction();
          }
          catch(Exception ex){
            System.out.println("Error rolling back. Connection to DB lost.");
          }
        }
        break;
	
	//Update Article	
		case 17:
   System.out.println("17. Update an Article in DB:");
   try{
    System.out.println("Enter Article ID of the article you wish to update:");
		artId = sc.nextInt();
		sc.nextLine();
		
        System.out.println("Which parameter do you want to update:");
        System.out.println("1. Article ID");
        System.out.println("2. Topic");
        System.out.println("3. Title");
        System.out.println("4. Date");
        System.out.println("5. Text");
        
        int ch = sc.nextInt();
        sc.nextLine();
       query1="";
        switch(ch){
          case 1:
          System.out.println("Enter updated Article ID: ");
          int newArtId = sc.nextInt();
          sc.nextLine();
          query1 = "UPDATE Article SET artID = \""+ newArtId+"\" WHERE artID = "+artId+";";
          break;
          case 2:
          System.out.println("Enter updated Topic: ");
          artTopic = sc.nextLine();
          query1 = "UPDATE Article SET topic = \""+ artTopic+"\" WHERE artID = "+artId+";";
          break;
          case 3:
          System.out.println("Enter updated Title: ");
          artTitle = sc.nextLine();
          query1 = "UPDATE Article SET title = \""+ artTitle+"\" WHERE artID = "+artId+";";
          break;
          case 4:
          System.out.println("Enter updated Date: ");
          date = sc.nextLine();
          if(!checkValidDate(date)){
                System.out.println("Invalid Date Format.");
                query1 = "";
                break;
          }
          date = "\"" + date + "\"";
          query1 = "UPDATE Article SET date = "+date+" WHERE artID = "+artId+";";
          break;
          case 5:
          System.out.println("Enter updated Text: ");
          artText = sc.nextLine();
          query1 = "UPDATE Article SET text = \""+ artText+"\" WHERE artID = "+artId+";";
          break;
          default:
          break;
        }
      }
      catch(InputMismatchException e){
        e.printStackTrace();
        System.out.println("Input type error!");
        sc.nextLine();
        break;
    }  
      if(!query1.equals("")) {

		try{ 
          updateCount = dbManager.executeUpdate(query1);
          if(updateCount>0)
             System.out.println("Successfully updated article in DB");
          else 
          System.out.println("No matching records found in DB!");     
        }
        catch(Exception e){
          e.printStackTrace();
          System.out.println("Error updating article in DB!");
        }
        }
        break;
        
        
	  //Add Article text		
    case 18:
    text = "";
    System.out.println("18. Add (append) Text to an Article in DB:");
    try{
      System.out.println("Enter Article ID for the Article:");
  		artId = sc.nextInt();
      sc.nextLine();
      System.out.println("To append text to this Article, please type below:");
  		artText = sc.nextLine();
   
    }
    catch(InputMismatchException e){
      e.printStackTrace();
      System.out.println("Input type error!");
      sc.nextLine();
      break;
    } 
		query1 = "SELECT text FROM Article WHERE artID = "+artId+";";
		
		try{
      dbManager.startTransaction();
      rs = dbManager.executeQuery(query1);
      while(rs.next()){        
        text = rs.getString("text");
      }
      artText = text + " " + artText;
      query2 = "UPDATE Article SET text = \"" + artText + "\" WHERE artID = "+artId+";";
		  updateCount = dbManager.executeUpdate(query2);
      dbManager.commitTransaction();
      if(updateCount>0)
        System.out.println("Successfully added text to the article in DB");
      else
        System.out.println("No matching records found in DB!");
    }
    catch(Exception e){
      e.printStackTrace();
      System.out.println("Error adding text to the article in DB!");
      try{
        dbManager.rollbackTransaction();
      }
      catch(Exception ex){
        System.out.println("Error rolling back. Connection to DB lost.");
      }
    }
    break;
		
	  //Update Article text		
		case 19:
    System.out.println("19. Update Text to an Article in DB:");
    try{
      System.out.println("Enter a Article ID of the Article for which you wish to update Text:");
  		artId = sc.nextInt();
  		sc.nextLine();
  		System.out.println("Enter text to be updated to the Article:");
  		artText = sc.nextLine();
		}
    catch(InputMismatchException e){
      e.printStackTrace();
      System.out.println("Input type error!");
      sc.nextLine();
      break;
    }
		query1 =  "UPDATE Article SET text = \"" + artText + "\" WHERE artID = " + artId+ ";";
		try{   
      updateCount = dbManager.executeUpdate(query1);
      if(updateCount>0)
        System.out.println("Successfully updated issue text in DB");
      else 
        System.out.println("No matching records found in DB!");
    }
    catch(Exception e){
      e.printStackTrace();
      System.out.println("Error updating issue text in DB!");
    }
    break;
	
	
	//Delete Article	
		case 20:
    System.out.println("20. Delete an Article from DB:");
    try{
		System.out.println("Enter article ID of the article you wish to delete:");
		artId = sc.nextInt();
		sc.nextLine();
   }
      catch(InputMismatchException e){
        e.printStackTrace();
        System.out.println("Input type error!");
        sc.nextLine();
        break;
    }  
		query1 =  "DELETE FROM Article WHERE artID = "+artId+";";
	  
		try{
         updateCount = dbManager.executeUpdate(query1);
          if(updateCount>0)
          System.out.println("Successfully deleted article from  DB");
           else 
          System.out.println("No matching records found in DB!");
        }
        catch(Exception e){
          e.printStackTrace();
          System.out.println("Error deleted article from DB!");
          
        }
        break;
		
	//Author assigned to article	
		case 21:
    System.out.println("21. Assign Author to article:");
    try{
		System.out.println("Enter Article Id of the article for which you want to assign an author:");
		artId = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter Staff Id of the author:");
		staffId = sc.nextInt();
		sc.nextLine();
   }
      catch(InputMismatchException e){
        e.printStackTrace();
        System.out.println("Input type error!");
        sc.nextLine();
        break;
    }
		query1 = "INSERT INTO AuthorAssignedArticle (staffID, artID) VALUES ("+staffId+","+artId+");";
		
		try{
          dbManager.executeUpdate(query1);
          System.out.println("Successfully assigned author to article in DB");
        }
        catch(Exception e){
		  e.printStackTrace();
          System.out.println("Error assiging author to article in DB!");
        }
        break;
		

        
    //Find Articles by topic	
		case 22:
    System.out.println("22. Find Articles by Topic in DB:");
		System.out.println("Enter a topic to find Articles:");
		topic = sc.nextLine();
		query1 = "select Article.artID, topic, Article.title, Article.date, text, Publication.pubID, Publication.title, Issue.IssueID, Issue.date from Article natural join IssueContains inner join Issue on IssueContains.issueID = Issue.issueID and IssueContains.pubID = Issue.pubID inner join Publication on Issue.pubID = Publication.pubID WHERE topic = \""+ topic+"\";";
		
		try{
         
       rs = dbManager.executeQuery(query1);
            ResultSetMetaData rsmd = rs.getMetaData();
            int numCol = rsmd.getColumnCount();
            System.out.println("Matching records:");
            while(rs.next()){
              System.out.println("-------------------------------------------------");
              System.out.println("Article details:"); 
              System.out.println("Article ID: " + rs.getInt("Article.artID"));
              System.out.println("Article topic: " + rs.getString("topic"));
              System.out.println("Article title: " + rs.getString("Article.title"));
              System.out.println("Article date: " + rs.getString("Article.date"));
              System.out.println("Article text: " + rs.getString("text"));
              System.out.println("");
              System.out.println("Publication and Issue details of the Article: ");
              System.out.println("Publication ID: " + rs.getInt("Publication.pubID"));
              System.out.println("Publication title: " + rs.getString("Publication.title"));
              System.out.println("Issue ID: " + rs.getInt("Issue.IssueID"));
              System.out.println("Issue date: " + rs.getString("Issue.date"));
              System.out.println("-------------------------------------------------");
              System.out.println("");
            }
         
        }
        catch(Exception e){
		  e.printStackTrace();
          System.out.println("Error finding books by given topic in DB!");

        }
        break;    
        
    //Find Articles by date	
		case 23:
    System.out.println("23. Find Articles by Date in DB:");
		System.out.println("Enter a Start Date [YYYY-MM-DD] to find Articles (Press Enter for today's date):"); 
		startDate = sc.nextLine();
		if(!checkValidDate(startDate)){
     if(startDate.equals("")){
     
      startDate = LocalDate.now().toString();
     }
     else{
             System.out.println("Invalid Date Format");
             break;
                }
     }     		
    System.out.println("Enter End Date [YYYY-MM-DD] to find Articles (Press Enter for today's date):"); 
		endDate = sc.nextLine();
		if(!checkValidDate(endDate)){
     if(endDate.equals("")){
      endDate = LocalDate.now().toString();
     }
     else{
             System.out.println("Invalid Date Format");
             break;
                }
     }    
		query1 = "select Article.artID, topic, Article.title, Article.date, text, Publication.pubID, Publication.title, Issue.IssueID, Issue.date from Article natural join IssueContains inner join Issue on IssueContains.issueID = Issue.issueID and IssueContains.pubID = Issue.pubID inner join Publication on Issue.pubID = Publication.pubID WHERE Article.date IS NOT NULL AND Article.date BETWEEN \""+ startDate+"\" AND  \""+ endDate+"\";";
		
		try{
         
       rs = dbManager.executeQuery(query1);
            ResultSetMetaData rsmd = rs.getMetaData();
            int numCol = rsmd.getColumnCount();
            System.out.println("Matching records:");
            while(rs.next()){
              System.out.println("-------------------------------------------------");
              System.out.println("Article details:"); 
              System.out.println("Article ID: " + rs.getInt("Article.artID"));
              System.out.println("Article topic: " + rs.getString("topic"));
              System.out.println("Article title: " + rs.getString("Article.title"));
              System.out.println("Article date: " + rs.getString("Article.date"));
              System.out.println("Article text: " + rs.getString("text"));
              System.out.println("");
              System.out.println("Publication and Issue details of the Article: ");
              System.out.println("Publication ID: " + rs.getInt("Publication.pubID"));
              System.out.println("Publication title: " + rs.getString("Publication.title"));
              System.out.println("Issue ID: " + rs.getInt("Issue.IssueID"));
              System.out.println("Issue date: " + rs.getString("Issue.date"));
              System.out.println("-------------------------------------------------");
              System.out.println("");
            }
         
        }
        catch(Exception e){
		  e.printStackTrace();
          System.out.println("Error finding books by given topic in DB!");

        }
        	

        break;  
        
     //Find Articles by author name	
		case 24:
    System.out.println("24. Find Articles by Author Name in DB:");
		System.out.println("Enter Author name to find Articles:");
		name = sc.nextLine();
		query1 = "select Article.artID, topic, Article.title, Article.date, text, Publication.pubID, Publication.title, Issue.IssueID, Issue.date from Article natural join IssueContains inner join Issue on IssueContains.issueID = Issue.issueID and IssueContains.pubID = Issue.pubID inner join Publication on Issue.pubID = Publication.pubID INNER JOIN AuthorAssignedArticle ON Article.artID = AuthorAssignedArticle.artID WHERE staffID IN (SELECT staffID FROM Staff WHERE username = \"" + name+ "\");";
		
		try{
         
       rs = dbManager.executeQuery(query1);
            ResultSetMetaData rsmd = rs.getMetaData();
            int numCol = rsmd.getColumnCount();
            System.out.println("Matching records:");
            while(rs.next()){
              System.out.println("-------------------------------------------------");
              System.out.println("Article details:"); 
              System.out.println("Article ID: " + rs.getInt("Article.artID"));
              System.out.println("Article topic: " + rs.getString("topic"));
              System.out.println("Article title: " + rs.getString("Article.title"));
              System.out.println("Article date: " + rs.getString("Article.date"));
              System.out.println("Article text: " + rs.getString("text"));
              System.out.println("");
              System.out.println("Publication and Issue details of the Article: ");
              System.out.println("Publication ID: " + rs.getInt("Publication.pubID"));
              System.out.println("Publication title: " + rs.getString("Publication.title"));
              System.out.println("Issue ID: " + rs.getInt("Issue.IssueID"));
              System.out.println("Issue date: " + rs.getString("Issue.date"));
              System.out.println("-------------------------------------------------");
              System.out.println("");
            }
         
        }
        catch(Exception e){
		  e.printStackTrace();
          System.out.println("Error finding books by given topic in DB!");

        }
        break;         
        
        //Enter payment	
		case 25:
     System.out.println("25. Enter Payment Info in DB:");
     try{
    System.out.println("Enter a Payment ID for the payment:");
    payId = sc.nextInt();
    sc.nextLine();
    System.out.println("Enter generated Date for Payment:");
		generatedDate = sc.nextLine();
		if(!checkValidDate(generatedDate)){
          		if(generatedDate.equals("")){
            generatedDate = LocalDate.now().toString();
          }
          else{
            System.out.println("Invalid Date Format!");
		break;
          }
        }	
		System.out.println("Enter amount for Payment:");
		amount = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter staff ID of the staff associated with this Payment:");
		staffId = sc.nextInt();
		sc.nextLine();
    }
      catch(InputMismatchException e){
        e.printStackTrace();
        System.out.println("Input type error!");
        sc.nextLine();
        break;
    }
		query1 = "INSERT INTO Payment(payID, generatedDate, amount, claimedDate) VALUES ("+payId+",\""+generatedDate+"\","+amount+",NULL);";
		query2 =  "INSERT INTO GetsPaid VALUES ("+staffId+","+payId+");";;

		try{
          dbManager.startTransaction();
          dbManager.executeUpdate(query1);
		  dbManager.executeUpdate(query2);
        dbManager.commitTransaction();
          System.out.println("Successfully entered payment in DB");
        }
        catch(Exception e){
		  e.printStackTrace();
         System.out.println("Error entering payment in DB!");
         try{
            dbManager.rollbackTransaction();
          }
          catch(Exception ex){
            System.out.println("Error rolling back. Connection to DB lost.");
          }
        }
        break;
        
		
	//Find payment by payment ID	
		case 26:
    checkFlag = false;
    System.out.println("26. Find Payment by Pay ID in DB:");
	  try{
		System.out.println("Enter Payment ID to find Payment:");
		payId = sc.nextInt();
		sc.nextLine();
   }
      catch(InputMismatchException e){
        e.printStackTrace();
        System.out.println("Input type error!");
        sc.nextLine();
        break;
    }
		query1 = " SELECT * FROM Payment NATURAL JOIN GetsPaid NATURAL JOIN Staff WHERE payID = " + payId+ ";";
    query2 = "  SELECT Payment.payID, generatedDate, amount, claimedDate, Orders.orderID, orderDate,price, shipCost, numCopies, Distributors.distID, Distributors.name,  Distributors.contactPerson  FROM Payment NATURAL JOIN PaymentConnectedToOrder NATURAL JOIN DistGetsPayment NATURAL JOIN Orders NATURAL JOIN Distributors WHERE payID = " + payId+ ";";
	
		try{
          
          rs = dbManager.executeQuery(query1); 
            ResultSetMetaData rsmd = rs.getMetaData();
            int numCol = rsmd.getColumnCount();
            System.out.println("Matching records:");
            while(rs.next()){
            checkFlag = true;
            System.out.println("-------------------------------------------------"); 
              System.out.println("Payment Info:"); 
              System.out.println("Payment ID: " + rs.getInt("payID"));
              System.out.println("Generated Date: " + rs.getString("generatedDate"));
              System.out.println("Amount: " + rs.getDouble("amount"));
              System.out.println("Claimed date: " + rs.getString("claimedDate"));
              System.out.println("");
              System.out.println("Staff details associated with the Payment: ");
              System.out.println("Staff ID: " + rs.getInt("staffID"));
              System.out.println("Staff Name: " + rs.getString("name"));
              System.out.println("-------------------------------------------------");
              System.out.println("");
            }
            if(!checkFlag) {
            rs = dbManager.executeQuery(query2); 
            while(rs.next()){
         System.out.println("-------------------------------------------------"); 
              System.out.println("Payment Info:"); 
              System.out.println("Payment ID: " + rs.getInt("Payment.payID"));
              System.out.println("Generated Date: " + rs.getString("generatedDate"));
              System.out.println("Amount: " + rs.getInt("amount"));
              System.out.println("Claimed date: " + rs.getString("claimedDate"));
              System.out.println("");
              System.out.println("Order details associated with the Payment: ");
              System.out.println("Order ID: " + rs.getInt("Orders.orderID"));
              System.out.println("Order Date: " + rs.getString("orderDate"));
              System.out.println("Price: " + rs.getDouble("price"));
              System.out.println("Shipping Cost: " + rs.getDouble("shipCost"));
              System.out.println("No. of Copies: " + rs.getInt("numCopies"));
               System.out.println("");
              System.out.println("Distributor details associated with the Payment: ");
              System.out.println("Distributor ID: " + rs.getInt("Distributors.distID"));
              System.out.println("Name: " + rs.getString("Distributors.name"));
              System.out.println("Contact Person: " + rs.getString("Distributors.contactPerson"));
              System.out.println("-------------------------------------------------");
              System.out.println("");
            }
            }
            
        
        }
        catch(Exception e){
		  e.printStackTrace();
          System.out.println("Error finding payment by given Payment ID in DB!");
        }
        break;
	
	//Find payment by Claimed Status	
		case 27:
	  System.out.println("27. Find Payment by Claimed Status in DB:");
     checkFlag = false;
		try{
    System.out.println("Enter Claimed Status [Enter \"1\"  for IS NULL and \"2\" for NOT NULL] to find Payment:");
		int input = sc.nextInt();
    claimedStatus = "";
    sc.nextLine();
    if(input == 1) 
      claimedStatus = "IS NULL";
    else if(input == 2)
       claimedStatus = "IS NOT NULL";
    else {
       System.out.println("Invalid Input!");  
       break;
    } 
    }
      catch(InputMismatchException e){
        e.printStackTrace();
        System.out.println("Input type error!");
        sc.nextLine();
        break;
    }
		query1 = " SELECT * FROM Payment NATURAL JOIN GetsPaid NATURAL JOIN Staff WHERE claimedDate " + claimedStatus+ ";";
    query2 = " SELECT Payment.payID, generatedDate, amount, claimedDate, Orders.orderID, orderDate,price, shipCost, numCopies, Distributors.distID, Distributors.name,  Distributors.contactPerson  FROM Payment NATURAL JOIN PaymentConnectedToOrder NATURAL JOIN DistGetsPayment NATURAL JOIN Orders NATURAL JOIN Distributors WHERE claimedDate " + claimedStatus+ ";";
		
		try{
         rs = dbManager.executeQuery(query1); 
            ResultSetMetaData rsmd = rs.getMetaData();
            int numCol = rsmd.getColumnCount();
            System.out.println("Matching records:");
            while(rs.next()){
            checkFlag = true;
            System.out.println("-------------------------------------------------"); 
              System.out.println("Payment Info:"); 
              System.out.println("Payment ID: " + rs.getInt("payID"));
              System.out.println("Generated Date: " + rs.getString("generatedDate"));
              System.out.println("Amount: " + rs.getDouble("amount"));
              System.out.println("Claimed date: " + rs.getString("claimedDate"));
              System.out.println("");
              System.out.println("Staff details associated with the Payment: ");
              System.out.println("Staff ID: " + rs.getInt("staffID"));
              System.out.println("Staff Name: " + rs.getString("name"));
              System.out.println("-------------------------------------------------");
              System.out.println("");
            }
            if(!checkFlag) {
            rs = dbManager.executeQuery(query2); 
            while(rs.next()){
            System.out.println("-------------------------------------------------"); 
              System.out.println("Payment Info:"); 
              System.out.println("Payment ID: " + rs.getInt("Payment.payID"));
              System.out.println("Generated Date: " + rs.getString("generatedDate"));
              System.out.println("Amount: " + rs.getInt("amount"));
              System.out.println("Claimed date: " + rs.getString("claimedDate"));
              System.out.println("");
              System.out.println("Order details associated with the Payment: ");
              System.out.println("Order ID: " + rs.getInt("Orders.orderID"));
              System.out.println("Order Date: " + rs.getString("orderDate"));
              System.out.println("Price: " + rs.getDouble("price"));
              System.out.println("Shipping Cost: " + rs.getDouble("shipCost"));
              System.out.println("No. of Copies: " + rs.getInt("numCopies"));
               System.out.println("");
              System.out.println("Distributor details associated with the Payment: ");
              System.out.println("Distributor ID: " + rs.getInt("Distributors.distID"));
              System.out.println("Name: " + rs.getString("Distributors.name"));
              System.out.println("Contact Person: " + rs.getString("Distributors.contactPerson"));
              System.out.println("-------------------------------------------------");
              System.out.println("");
            }
            }
            
        }
        catch(Exception e){
		  e.printStackTrace();
          System.out.println("Error finding payment by given claimed Status in DB!");
        }
        break;
	
	
	
		
    default:
    System.out.println("Invalid Choice.");
    break;
    }


      System.out.println("Would you like to perform another Production operation? Enter 1 for Yes or 0 for N");
      choice = sc.nextInt();
      sc.nextLine();
      flag = (choice == 1) ? true : false;
    }
		
		
  }

  public static boolean checkValidEmail(String email) 
  { 
      String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                          "[a-zA-Z0-9_+&*-]+)*@" + 
                          "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                          "A-Z]{2,7}$"; 
                            
      Pattern pat = Pattern.compile(emailRegex); 
      if (email == null) 
          return false; 
      return pat.matcher(email).matches(); 
  } 

  public static void personnel(DBManager dbManager, Scanner sc){
    //switch case operations
    boolean flag = true;
    boolean breakflag = false;
    while(flag){
      try{
      System.out.println("-------------------------------------------------------------------------------------------");
      System.out.println("Determine what Personnel activity you want to perfrom:");
      System.out.println("1. Enter New Staff in DB");
      System.out.println("2. Update Staff Information");
      System.out.println("3. Delete a Staff from DB");
      System.out.println("4. Search for a Staff");
      System.out.println("-------------------------------------------------------------------------------------------");
      int choice = sc.nextInt();
      sc.nextLine();
      String query = "";
      String staffType, staffName, staffEmail, staffPhone, staffAddr, staffGender, staffUserName, staffPassword, staffDate;
      int staffID, staffAge;
      int staffSalary=0;
      switch(choice){
        case 1:
        try{
        System.out.println("-----------------------------");
        System.out.println("==> Enter New Staff to DB <==");
        System.out.println("-----------------------------");
        System.out.println("-- Fill out the following details for the new staff --");
        System.out.println("Enter Staff ID for the new staff:");
        staffID = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter name:");
        staffName = sc.nextLine();
        System.out.println("Enter age:");
        staffAge = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter Email Address:");
        staffEmail = sc.nextLine();
        if(!checkValidEmail(staffEmail)){
          System.out.println("Invalid Email Address Format");
          break;
        }
        System.out.println("Enter Phone Number:");
        staffPhone = sc.nextLine();
        if(staffPhone.length() != 10){
          System.out.println("Phone number should be 10 digits.");
          break;
        }
        System.out.println("Enter Gender: [M or F]");
        staffGender = sc.nextLine();
        if(!staffGender.equals("M") && !staffGender.equals("F")){
          System.out.println("Invalid Gender "+staffGender+"!");
          break;
        }
        System.out.println("Enter Street Address:");
        staffAddr = sc.nextLine();
        System.out.println("Enter username:");
        staffUserName = sc.nextLine();
        System.out.println("Enter password:");
        staffPassword = sc.nextLine();
        System.out.println("Enter Join Date [YYYY-MM-DD] (leave blank if today's date)");
        staffDate = sc.nextLine();
        if(!checkValidDate(staffDate)){
          if(staffDate.equals("")){
            staffDate = LocalDate.now().toString();
          }
          else{
            System.out.println("Invalid Date Format");
          }
        }
        System.out.println("What is the type of the new Staff?");
        System.out.println("1. Editor");
        System.out.println("2. Author");
        System.out.println("3. Publication Manager");
        System.out.println("4. Distribution Manager");
        System.out.println("5. Admin");
        int choicen = sc.nextInt();
        sc.nextLine();
        String query2 = null;
        breakflag = false;
        switch(choicen){
          case 1:
          System.out.println("What is the type of the new Editor?");
          System.out.println("1. Internal Staff Editor (Default)");
          System.out.println("2. External Invited Editor");
          int choicet = sc.nextInt();
          sc.nextLine();
          if(choicet == 2){
            staffSalary = 0;
            staffType = "external";
          }
          else{
            System.out.println("Enter Salary:");
            staffSalary = sc.nextInt();
            sc.nextLine();
            staffType = "internal";
          }
          query2 = "INSERT INTO Editor(staffID,type) VALUES (%d,\"%s\")";
          query2 = String.format(query2, staffID, staffType);
          break;
          case 2:
          System.out.println("What is the type of the new Author?");
          System.out.println("1. Internal Staff Author");
          System.out.println("2. External Invited Author (Default)");
          int choicea = sc.nextInt();
          sc.nextLine();
          if(choicea == 1){
            System.out.println("Enter Salary:");
            staffSalary = sc.nextInt();
            sc.nextLine();
            staffType = "internal";
          }
          else{
            staffSalary = 0;
            staffType = "external";
          }
          query2 = "INSERT INTO Author(staffID,type) VALUES (%d,\"%s\")";
          query2 = String.format(query2, staffID, staffType);
          break;
          case 3:
          System.out.println("Enter Salary:");
          staffSalary = sc.nextInt();
          sc.nextLine();
          query2 = "INSERT INTO PublicationManager(staffID) VALUES (%d)";
          query2 = String.format(query2, staffID);
          break;
          case 4:
          System.out.println("Enter Salary:");
          staffSalary = sc.nextInt();
          sc.nextLine();
          query2 = "INSERT INTO DistributionManager(staffID) VALUES (%d)";
          query2 = String.format(query2, staffID);
          break;
          case 5:
          System.out.println("Enter Salary:");
          staffSalary = sc.nextInt();
          sc.nextLine();
          break;
          default:System.out.println("Invalid Choice");
          breakflag = true;
          break;
        }
        if(breakflag){
          break;
        }
        query = "INSERT INTO Staff (staffID,name,age,email,phone,gender,address,joinDate,username,password,salary) VALUES (%d,\"%s\",%d,\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",%d)";
        query = String.format(query, staffID, staffName,staffAge,staffEmail,staffPhone,staffGender,staffAddr,staffDate,staffUserName,staffPassword,staffSalary);

        //Add to DB using transactions
        try{
          dbManager.startTransaction();
          //Step 1: Insert to Staff
          dbManager.executeUpdate(query);

          //Step 2: Insert to Children
          if(query2 != null){
            dbManager.executeUpdate(query2);
          }

          //Commit if everything goes well
          dbManager.commitTransaction();
          System.out.println("~~~ Successfully added New Staff to DB ~~~");
        }
        catch(Exception e){
          System.out.println("Error creating Staff member. Rolling back");
          try{
            dbManager.rollbackTransaction();
          }
          catch(Exception ex){
            System.out.println("Error rolling back. Connection to DB lost.");
          }
        }
        }
    	catch(InputMismatchException e){
	      e.printStackTrace();
	      System.out.println("Input type error!");
	      sc.nextLine();
	      break;
	    }
        break;
        case 2:
        System.out.println("-------------------------------");
        System.out.println("==> Update Staff Info in DB <==");
        System.out.println("-------------------------------");
        System.out.println("Enter Staff ID of Staff: ");
        staffID = sc.nextInt();
        sc.nextLine();
        //Check Valid staffID
        try{
          query = "SELECT staffID FROM Staff WHERE staffID="+staffID;
          ResultSet rs = dbManager.executeQuery(query);
          if(!rs.next()){
            System.out.println("Given staffID is not in DB. Try again.");
            break;
          }
        }
        catch(Exception e){
          System.out.println("Error searching given StaffID. ");
          break;
        }
        System.out.println("Which parameter do you want to update for Staff with ID "+staffID);
        System.out.println("1. Staff ID");
        System.out.println("2. Staff Name");
        System.out.println("3. Staff Age");
        System.out.println("4. Staff Email");
        System.out.println("5. Staff Phone");
        System.out.println("6. Staff Gender");
        System.out.println("7. Staff Address");
        System.out.println("8. Staff Join Date");
        System.out.println("9. Staff Username");
        System.out.println("10. Staff Password");
        System.out.println("11. Staff Salary");
        int choiceu = sc.nextInt();
        sc.nextLine();
        breakflag = false;
        switch(choiceu){
          case 1:
          System.out.println("Enter updated Staff ID: ");
          staffID = sc.nextInt();
          sc.nextLine();
          query = "UPDATE Staff SET staffID="+staffID+" WHERE staffID="+staffID;
          break;
          case 2:
          System.out.println("Enter updated Staff Name: ");
          staffName = sc.nextLine();
          query = "UPDATE Staff SET name=\""+staffName+"\" WHERE staffID="+staffID;
          break;
          case 3:
          System.out.println("Enter updated Staff Age: ");
          staffAge = sc.nextInt();
          sc.nextLine();
          query = "UPDATE Staff SET age="+staffAge+" WHERE staffID="+staffID;
          break;
          case 4:
          System.out.println("Enter updated Staff Email: ");
          staffEmail = sc.nextLine();
          query = "UPDATE Staff SET email=\""+staffEmail+"\" WHERE staffID="+staffID;
          break;
          case 5:
          System.out.println("Enter updated Staff Phone: ");
          staffPhone = sc.nextLine();
          query = "UPDATE Staff SET phone=\""+staffPhone+"\" WHERE staffID="+staffID;
          break;
          case 6:
          System.out.println("Enter updated Staff Gender: ");
          staffGender = sc.nextLine();
          if(!staffGender.equals("M") || !staffGender.equals("F")){
            System.out.println("Invalid Gender");
            breakflag = true;
            break;
          }
          query = "UPDATE Staff SET gender=\""+staffGender+"\" WHERE staffID="+staffID;
          break;
          case 7:
          System.out.println("Enter updated Staff Address: ");
          staffAddr = sc.nextLine();
          query = "UPDATE Staff SET address=\""+staffAddr+"\" WHERE staffID="+staffID;
          break;
          case 8:
          System.out.println("Enter updated Staff Join Date: ");
          staffDate = sc.nextLine();
          if(!checkValidDate(staffDate)){
            if(staffDate.equals("")){
              staffDate = LocalDate.now().toString();
            }
            else{
              System.out.println("Invalid Date Format");
              breakflag = true;
              break;
            }
          }
          query = "UPDATE Staff SET joinDate=\""+staffDate+"\" WHERE staffID="+staffID;
          break;
          case 9:
          System.out.println("Enter updated Staff Username: ");
          staffUserName = sc.nextLine();
          query = "UPDATE Staff SET username=\""+staffUserName+"\" WHERE staffID="+staffID;
          break;
          case 10:
          System.out.println("Enter updated Staff Password: ");
          staffPassword = sc.nextLine();
          query = "UPDATE Staff SET password=\""+staffPassword+"\" WHERE staffID="+staffID;
          break;
          case 11:
          System.out.println("Enter updated Staff Salary: ");
          staffSalary = sc.nextInt();
          sc.nextLine();
          query = "UPDATE Staff SET salary="+staffSalary+" WHERE staffID="+staffID;
          break;
          default:System.out.println("Invalid Choice");
          breakflag = true;
          break;
        }
        if(breakflag){
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
        System.out.println("------------------------------");
        System.out.println("==> Delete a Staff from DB <==");
        System.out.println("------------------------------");
        System.out.println("Enter Staff ID of staff to delete: ");
        staffID = sc.nextInt();
        sc.nextLine();
        try{
          query = "SELECT staffID FROM Staff WHERE staffID="+staffID;
          ResultSet rs = dbManager.executeQuery(query);
          if(!rs.next()){
            System.out.println("Given staffID is not in DB. Try again.");
            break;
          }
        }
        catch(Exception e){
          System.out.println("Error searching given StaffID. ");
          break;
        }
        query = "DELETE FROM Staff WHERE staffID="+staffID;
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
        System.out.println("--------------------------");
        System.out.println("==> Search for a Staff <==");
        System.out.println("--------------------------");
        System.out.println("Using which field do you want to search?");
        System.out.println("1. Staff ID");
        System.out.println("2. Staff Name");
        System.out.println("3. Staff Email");
        System.out.println("4. Staff Type");
        System.out.println("5. Staff Address");
        System.out.println("6. Staff Username");
        int choices = sc.nextInt();
        sc.nextLine();
        switch(choices){
          case 1:
          System.out.println("Search by Staff ID: ");
          staffID = sc.nextInt();
          sc.nextLine();
          query = "SELECT * FROM Staff WHERE staffID="+staffID;
          break;

          case 2:
          System.out.println("Search by Staff Name: ");
          staffName = sc.nextLine();
          query = "SELECT * FROM Staff WHERE name LIKE \"%"+staffName+"%\"";
          break;

          case 3:
          System.out.println("Search by Staff Email: ");
          staffEmail = sc.nextLine();
          query = "SELECT * FROM Staff WHERE email LIKE \"%"+staffEmail+"%\"";
          break;

          case 4:
          System.out.println("Search by Staff Type: ");
          System.out.println("1. Editor");
          System.out.println("2. Author");
          System.out.println("3. Publication Manager");
          System.out.println("4. Distribution Manager");
          System.out.println("5. Admin");
          int choiceb = sc.nextInt();
          sc.nextLine();
          switch(choiceb){
            case 1:
            query = "SELECT * FROM Editor NATURAL JOIN Staff";
            break;
            case 2:
            query = "SELECT * FROM Author NATURAL JOIN Staff";
            break;
            case 3:
            query = "SELECT * FROM PublicationManager NATURAL JOIN Staff";
            break;
            case 4:
            query = "SELECT * FROM DistributionManager NATURAL JOIN Staff";
            break;
            case 5:
            query = "SELECT * "+
                    "FROM Staff"+
                    "WHERE staffID NOT IN "+
                    "("+
                      "SELECT staffID FROM Editor"+
                      "UNION"+
                      "SELECT staffID FROM Author"+
                      "UNION"+
                      "SELECT staffID FROM PublicationManager"+
                      "UNION"+
                      "SELECT staffID FROM DistributionManager"+
                    ");";
            break;
            default:
            query = null;
            break;

          }
          break;

          case 5:
          System.out.println("Search by Staff Address: ");
          staffAddr = sc.nextLine();
          query = "SELECT * FROM Staff WHERE address LIKE \"%"+staffAddr+"%\"";
          break;

          case 6:
          System.out.println("Search by Staff Username: ");
          staffUserName = sc.nextLine();
          query = "SELECT * FROM Staff WHERE username LIKE \"%"+staffUserName+"%\"";
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
    
        default:System.out.println("Invalid Choice");
        break;
      }
      }
      catch(Exception e){
        System.out.println("Invalid Input");
      }
      //Repeat?
      System.out.println("");
      System.out.println("Would you like to perform another Personnel operation? Enter 1 for Yes or 0 for N");
      int choicerepeat = sc.nextInt();
      sc.nextLine();
      flag = (choicerepeat == 1) ? true : false;
    }
  }

  public static void distribution(DBManager dbManager, Scanner sc){
    //switch case operations
    boolean flag = true;
    while(flag){
      try{
      System.out.println("-------------------------------------------------------------------------------------------");
      System.out.println("Determine what Distribution activity you want to perfrom:");
      System.out.println("1. Enter New Distributor Info to DB");
      System.out.println("2. Update Distributor Info in DB");
      System.out.println("3. Delete a Distributor from DB");
      System.out.println("4. Enter Distributor and Order Info for an Issue of Journal or Magazine for a certain date");
      System.out.println("5. Enter Distributor and Order Info for an Edition of Book for a certain date");
      System.out.println("6. Generate Bill for a Distributor for a particular order");
      System.out.println("7. Change outstanding balance of a distributor on receipt of a payment");
      System.out.println("8. View Bills Associated to a Distributor or Order");
      System.out.println("9. View Payments Associated to a Distributor or Order");
      System.out.println("10. Search for a distributor");
      System.out.println("-------------------------------------------------------------------------------------------");
      int choice = sc.nextInt();
      sc.nextLine();
      String query = "";
      int distID,ordID,numCopies,pubID,issueID,editionID,payID,billID;
      String distName,contactPerson,distType,distStAddr,distCity,distPhone,orderDate,deliveryDate,billDetails;
      double distBal,priceCopy,shipCost,billAmt;
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
        System.out.println("Enter Type of the new distributor (e.g., bookseller, wholesale, library):");
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
        //Check Valid distID
        try{
          query = "SELECT distID FROM Distributors WHERE distID="+distID;
          ResultSet rs = dbManager.executeQuery(query);
          if(!rs.next()){
            System.out.println("Given distID is not in DB. Try again.");
            break;
          }
        }
        catch(Exception e){
          System.out.println("Error searching given distID. ");
          break;
        }
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
        //Check Valid distID
        try{
          query = "SELECT distID FROM Distributors WHERE distID="+distID;
          ResultSet rs = dbManager.executeQuery(query);
          if(!rs.next()){
            System.out.println("Given distID is not in DB. Try again.");
            break;
          }
        }
        catch(Exception e){
          System.out.println("Error searching given distID. ");
          break;
        }
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
        try{
        System.out.println("---------------------------------------------------");
        System.out.println("==> Enter Distributor & Order Info for an Issue <==");
        System.out.println("==>  of Journal or Magazine for a certain date  <==");
        System.out.println("---------------------------------------------------");

        System.out.println("--Identify Publication for the Order--");
        System.out.println("Enter Publication ID of the Journal or Magazine:");
        pubID = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter Issue ID of the Issue of the Journal or Magazine for the Order:");
        issueID = sc.nextInt();
        sc.nextLine();
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
        sc.nextLine();
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
        sc.nextLine();
        System.out.println("Enter Number of Copies for the Order: ");
        numCopies = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter Price of Each Copy: ");
        priceCopy = sc.nextDouble();
        sc.nextLine();
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
        }
    	catch(InputMismatchException e){
	      e.printStackTrace();
	      System.out.println("Input type error!");
	      sc.nextLine();
	      break;
	    }
        break;

        case 5:
        try{
        System.out.println("-----------------------------------------------");
        System.out.println("==>  Enter Distributor and Order Info for   <==");
        System.out.println("==>  an Edition of Book for a certain date  <==");
        System.out.println("-----------------------------------------------");

        System.out.println("--Identify Publication for the Order--");
        System.out.println("Enter Publication ID of the Book:");
        pubID = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter Edition ID of the Book for the Order:");
        editionID = sc.nextInt();
        sc.nextLine();
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
        sc.nextLine();
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
        sc.nextLine();
        System.out.println("Enter Number of Copies for the Order: ");
        numCopies = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter Price of Each Copy: ");
        priceCopy = sc.nextDouble();
        sc.nextLine();
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
          e.printStackTrace();
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
        }
    	catch(InputMismatchException e){
	      e.printStackTrace();
	      System.out.println("Input type error!");
	      sc.nextLine();
	      break;
	    }
        break;

        case 6:
        try{
        System.out.println("--------------------------------------------------------------");
        System.out.println("==> Generate Bill for a Distributor for a particular order <==");
        System.out.println("--------------------------------------------------------------");

        System.out.println("--Identify Order--");
        System.out.println("Enter Order ID of the Order:");
        ordID = sc.nextInt();
        sc.nextLine();
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

        //Do All Updates in Transactions
        try{
          //start transaction. set auto-commit false
          dbManager.startTransaction();

          // Step 1: Generate Bill
          billAmt = (priceCopy*numCopies)+shipCost;
          billDetails = "-------------------------BILL-------------------------------\n";
          billDetails += "Order ID: "+ordID+"                       Bill Date: "+LocalDate.now().toString()+"\n";
          billDetails += "Publication: "+pubTitle+" (Pub ID: "+pubID+")\n";
          if(pubType=="Issue"){
            billDetails += "Issue ID: "+issueID+"\n";
          }
          else{
            billDetails += "Edition ID: "+editionID+"\n";
          }
          billDetails += "Distributor: "+distName+" (Dist ID: "+distID+") City: "+distCity+"\n";
          billDetails += "\n";
          billDetails += "Number of Copies: "+numCopies+"  x Price Per Copy: "+priceCopy+"\n";
          billDetails += "Shipping Cost: "+shipCost+"\n";
          billDetails += "------------------------------------------------------------\n";
          billDetails += "Total Amount Owed: "+billAmt+"\n";
          billDetails += "------------------------------------------------------------\n";
          query = "INSERT INTO Bill (billAmt,billDetails) VALUES (%f, \"%s\")";
          query = String.format(query, billAmt, billDetails);
          billID = dbManager.executeUpdateGetKeys(query).get(0); //get auto_incremented inserted key
          
          //Step 2: Associate the bill with the distributor.
          query = "INSERT INTO DistGetsBill (billID,distID) VALUES (%d,%d)";
          query = String.format(query,billID,distID);
          dbManager.executeUpdate(query);

          //Step 3: Associate the bill with the order
          query = "INSERT INTO BillConnectedToOrder (billID, orderID) VALUES (%d,%d)";
          query = String.format(query,billID,ordID);
          dbManager.executeUpdate(query);

          //Step 4: Update Balance of Distributor
          query = "UPDATE Distributors SET balance=balance+"+billAmt+" WHERE distID="+distID;
          dbManager.executeUpdate(query);

          //If all goes well, commit transaction
          dbManager.commitTransaction();

          //Print bill details
          System.out.println("~~~ Successfully generated bill and updated outstanding balance of distributor. ~~~");
          System.out.println("==> Bill ID: "+billID);
          System.out.println(billDetails);
        }
        catch(Exception e){
          //If anything fails rollback
          e.printStackTrace();
          System.out.println("Error updating balance of distributor. Rolling back.");
          try{
            dbManager.rollbackTransaction();
            System.out.println("Rolled back DB to savepoint");
          }
          catch(Exception ex){
            System.out.println("Error rolling back. DB not connected");
          }
          break;
        }
        }
    	catch(InputMismatchException e){
	      e.printStackTrace();
	      System.out.println("Input type error!");
	      sc.nextLine();
	      break;
	    }
        break;

        case 7:
        try{
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("==> Change outstanding balance of a distributor on receipt of a payment <==");
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("Enter Order ID connected to payment: ");
        ordID = sc.nextInt();
        sc.nextLine();
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
        sc.nextLine();

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
        }
    	catch(InputMismatchException e){
	      e.printStackTrace();
	      System.out.println("Input type error!");
	      sc.nextLine();
	      break;
	    }
        break;
        
        case 8:
        System.out.println("---------------------------------------------------------");
        System.out.println("==> View Bills Associated to a Distributor or an Order<==");
        System.out.println("---------------------------------------------------------");
        System.out.println("View bills associated to - ");
        System.out.println("1. A Distributor");
        System.out.println("2. An Order");
        int choice1 = sc.nextInt();
        sc.nextLine();
        String printdetails = "";
        switch(choice1){
          case 1: System.out.println("Enter Distributor ID: ");
          distID = sc.nextInt();
          sc.nextLine();
          query = "SELECT * from Bill NATURAL JOIN DistGetsBill WHERE distID="+distID;
          printdetails = "Bills Associated With Distributor With ID: "+distID;
          break;
          
          case 2: 
          System.out.println("Enter Order ID: ");
          ordID = sc.nextInt();
          sc.nextLine();
          query = "SELECT * from Bill NATURAL JOIN BillConnectedToOrder WHERE orderID="+ordID;
          printdetails = "Bills Associated With Order With ID: "+ordID;
          break;

          default:
          query = null;
          break;
        }
        if(query != null){
          try{
            ResultSet rs = dbManager.executeQuery(query);
            System.out.println(printdetails);
            while(rs.next()){
              billID = rs.getInt("billID");
              billAmt = rs.getDouble("billAmt");
              billDetails = rs.getString("billDetails");
              System.out.println("Bill ID: "+billID);
              System.out.println("Bill Amount: "+billAmt);
              System.out.println(billDetails);
              System.out.println("");
            }
          }
          catch(Exception e){
            System.out.println("Error finding bills for given input");
          }
        }
        break;

        case 9:
        System.out.println("------------------------------------------------------------");
        System.out.println("==> View Payments Associated to a Distributor or an Order<==");
        System.out.println("------------------------------------------------------------");
        System.out.println("View payments associated to - ");
        System.out.println("1. A Distributor");
        System.out.println("2. An Order");
        int choice2 = sc.nextInt();
        sc.nextLine();
        String printdetails2 = "";
        switch(choice2){
          case 1: System.out.println("Enter Distributor ID: ");
          distID = sc.nextInt();
          sc.nextLine();
          query = "SELECT * from Payment NATURAL JOIN DistGetsPayment NATURAL JOIN PaymentConnectedToOrder NATURAL JOIN Orders NATURAL JOIN OrderHasIssue NATURAL JOIN Publication NATURAL JOIN Distributors WHERE distID="+distID+" UNION SELECT * from Payment NATURAL JOIN DistGetsPayment NATURAL JOIN PaymentConnectedToOrder NATURAL JOIN Orders NATURAL JOIN OrderHasEdition NATURAL JOIN Publication NATURAL JOIN Distributors WHERE distID="+distID;
          printdetails2 = "Payments Associated With Distributor With ID: "+distID;
          break;
          
          case 2: 
          System.out.println("Enter Order ID: ");
          ordID = sc.nextInt();
          sc.nextLine();
          query = "SELECT * from Payment NATURAL JOIN DistGetsPayment NATURAL JOIN PaymentConnectedToOrder NATURAL JOIN Orders NATURAL JOIN OrderHasIssue NATURAL JOIN Publication NATURAL JOIN Distributors WHERE orderID="+ordID+" UNION SELECT * from Payment NATURAL JOIN DistGetsPayment NATURAL JOIN PaymentConnectedToOrder NATURAL JOIN Orders NATURAL JOIN OrderHasEdition NATURAL JOIN Publication NATURAL JOIN Distributors WHERE orderID="+ordID;
          printdetails2 = "Payments Associated With Order With ID: "+ordID;
          break;

          default:
          query = null;
          break;
        }
        if(query != null){
          try{
            ResultSet rs = dbManager.executeQuery(query);
            System.out.println(printdetails2);
            while(rs.next()){
              //Payment Info
              System.out.println("--- Payment Information ---");
              System.out.println("Pay ID: "+rs.getInt("payID"));
              System.out.println("Amount: "+rs.getDouble("amount"));
              System.out.println("Payment Date: "+rs.getString("generatedDate"));

              //Publication Info
              System.out.println("\n--- Publication Information ---");
              System.out.println("Publication ID: "+rs.getInt("pubID"));
              System.out.println("Title: "+rs.getString("title"));
              System.out.println("Issue or Edition ID: "+rs.getInt("issueID"));

              //Order Info
              System.out.println("\n--- Order Information ---");
              System.out.println("Order ID: "+rs.getInt("orderID"));
              System.out.println("Order Date: "+rs.getString("orderDate"));
              System.out.println("Delivery Date: "+rs.getString("deliveryDate"));
              System.out.println("Price Per Copy: "+rs.getDouble("price")+" x Number of Copies: "+rs.getInt("numCopies"));
              System.out.println("Shipping Cost: "+rs.getDouble("shipCost"));
              System.out.println("Total Due: "+((rs.getDouble("price")*rs.getInt("numCopies"))+rs.getDouble("shipCost")));


              //Distributor Info
              System.out.println("\n--- Distributor Information ---");
              System.out.println("Dist ID: "+rs.getInt("distID"));
              System.out.println("Distributor Name: "+rs.getString("name"));
              System.out.println("Distributor Type: "+rs.getString("type"));
              System.out.println("Distributor Contact Person: "+rs.getString("contactPerson")+" Phone: "+rs.getString("phone"));
              System.out.println("Distributor Address"+rs.getString("strAddr")+" City:"+rs.getString("city"));
              System.out.println("");
            }
          }
          catch(Exception e){
            System.out.println("Error finding bills for given input");
          }
        }
        break;
        
        case 10:
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
      }
      catch(Exception e){
        System.out.println("Invalid Input");
      }
      //Repeat?
      System.out.println("");
      System.out.println("Would you like to perform another Distribution operation? Enter 1 for Yes or 0 for N");
      int choicerepeat = sc.nextInt();
      sc.nextLine();
      flag = (choicerepeat == 1) ? true : false;
    }
  }
  
  public static String get_start_and_end_date(Scanner sc)	{
    System.out.println("Enter the start date (yyyy-mm-dd). Leave empty if you want today's date:");
    String start_date = sc.nextLine();
    if(!checkValidDate(start_date))	{
      if(start_date.equals("")) {
        start_date = LocalDate.now().toString();
      }
      else {
        System.out.println("Invalid Date Format. Please enter correctly.");
      }
    }
    // check the validity of the date
    System.out.println("Enter the end date (yyyy-mm-dd). Leave empty if you want today's date:");
    String end_date = sc.nextLine();
    if(!checkValidDate(end_date)) {
      if(end_date.equals("")) {
        end_date = LocalDate.now().toString();
      }
      else {
        System.out.println("Invalid Date Format. Please enter correctly.");
      }
    }
    //concatenate to be split later
    String complete_date = start_date + "," + end_date;
    return (complete_date);
  }

  public static void reports(DBManager dbManager, Scanner sc) {
    //switch case operations
    boolean flag = true;
    while (flag) {
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
      System.out.println("11. The report of total payment to author's per time period.");
      System.out.println("12. The report of total payment to editors and authors for work type.");
      System.out.println("13. Monthly report of total payment to editors and authors for work type.");
      System.out.println("14. Exit reports.");

      int chosenval = sc.nextInt();
      sc.nextLine();
      String query = null;
      switch (chosenval) {
      // Monthly report for number of copies and total price of copies per publication per distributor
      case 1:
        String start_end_dates1 = get_start_and_end_date(sc);
        String[] dates_split1 = start_end_dates1.split(",");
        String start_date1 = dates_split1[0];
        String end_date1 = dates_split1[1];
      	// check the validity of the date
        query = "SELECT distID, pubID, numCopies, numCopies*price AS total_price FROM ( SELECT orderID, distID, pubID, issueID, IF (orderID IS NULL, NULL, NULL) AS editionID FROM DistAssigned NATURAL JOIN OrderHasIssue UNION SELECT orderID, distID, pubID, IF (orderID IS NULL, NULL, NULL) AS issueID, editionID FROM DistAssigned NATURAL JOIN OrderHasEdition) AS DistOrders NATURAL JOIN Orders WHERE orderDate between '" + start_date1 + "' and '" + end_date1 + "' GROUP BY distID, pubID;";
      break;

      // Monthly report on total revenue of the publication house
      case 2:
      	String start_end_dates2 = get_start_and_end_date(sc);
        String[] dates_split2 = start_end_dates2.split(",");
        String start_date2 = dates_split2[0];
        String end_date2 = dates_split2[1];
        // check the validity of the date
        query = "SELECT sum(amount) FROM Payment NATURAL JOIN DistGetsPayment WHERE generatedDate is NOT NULL AND generatedDate between '" + start_date2 + "' and '" + end_date2 + "';";
    	break;

      // Monthly report of total shipping cost
      case 3:
      	String start_end_dates3 = get_start_and_end_date(sc);
        String[] dates_split3 = start_end_dates3.split(",");
        String start_date3 = dates_split3[0];
        String end_date3 = dates_split3[1];
        query = "SELECT sum(shipCost) FROM Orders WHERE orderDate between'" + start_date3 + "' and '" + end_date3 + "';";
      break;

      // Monthly report of total salary expenses
      case 4:
        String start_end_dates4 = get_start_and_end_date(sc);
        String[] dates_split4 = start_end_dates4.split(",");
        String start_date4 = dates_split4[0];
        String end_date4 = dates_split4[1];
        query = "SELECT sum(amount) FROM Payment NATURAL JOIN GetsPaid WHERE generatedDate is NOT NULL AND generatedDate between '" + start_date4 + "' and '" + end_date4 + "';";
      break;

      // Monthly report of total expenses for the publication house
      case 5:
      	String start_end_dates5 = get_start_and_end_date(sc);
      	String[] dates_split5 = start_end_dates5.split(",");
      	String start_date5 = dates_split5[0];
      	String end_date5 = dates_split5[1];
      	query = "SELECT SUM(total) FROM( SELECT SUM(shipCost) AS total FROM Orders WHERE orderDate between'" + start_date5 + "' and '" + end_date5 + "' UNION SELECT SUM(amount) FROM( SELECT GetsPaid.payID, amount FROM GetsPaid INNER JOIN Payment ON GetsPaid.payID = Payment.payID WHERE generatedDate IS NOT NULL and generatedDate between '" + start_date5 + "' and '" + end_date5 + "') as SalaryPayment) as TotalExp;";
      break;

      // The total current number of distributors
      case 6:
        query = "SELECT count(distID) FROM Distributors;";
      break;

      // The report of total revenue since inception per city
      case 7:
        query = "SELECT city,SUM(amount) AS revenue FROM Payment NATURAL JOIN DistGetsPayment NATURAL JOIN Distributors GROUP BY city;";
      break;

      // The report of total revenue since inception per distributor
      case 8:
        query = "SELECT distID,name,SUM(amount) AS revenue FROM Payment NATURAL JOIN DistGetsPayment NATURAL JOIN Distributors GROUP BY distID;";
      break;

      // The report of total revenue since inception per location
      case 9:
        query = "SELECT SUM(amount), strAddr FROM Payment NATURAL JOIN DistGetsPayment NATURAL JOIN Distributors GROUP BY strAddr;";
      break;

      // The report of total payment to editors per time period
      case 10:
        String start_end_dates10 = get_start_and_end_date(sc);
        String[] dates_split10 = start_end_dates10.split(",");
        String start_date10 = dates_split10[0];
        String end_date10 = dates_split10[1];
        query = "SELECT SUM(amount) AS total_payment FROM Payment NATURAL JOIN GetsPaid NATURAL JOIN Editor WHERE generatedDate IS NOT NULL AND generatedDate between '" + start_date10 + "' and '" + end_date10 + "';";
      break;

      // The report of total payment to author's per time period
      case 11:
        String start_end_dates11 = get_start_and_end_date(sc);
        String[] dates_split11 = start_end_dates11.split(",");
        String start_date11 = dates_split11[0];
        String end_date11 = dates_split11[1];
        query = "SELECT SUM(amount) AS total_payment FROM Payment NATURAL JOIN GetsPaid NATURAL JOIN Author WHERE generatedDate IS NOT NULL AND generatedDate between '" + start_date11 + "' and '" + end_date11 + "' ;";
      break;

      // The report of total payment to editors and authors for work type
      case 12:
        query = "SELECT IF(staffID in (SELECT staffID FROM Editor),'editorial work', IF(staffID in (SELECT staffID FROM AuthorAssignedEdition), 'book authorship','article authorship')) AS workType, SUM(amount) as totalPayment FROM Payment NATURAL JOIN GetsPaid GROUP BY workType;";
      break;

      // The report of total payment to editors and authors for work type
      case 13:
        String start_end_dates12 = get_start_and_end_date(sc);
        String[] dates_split12 = start_end_dates12.split(",");
        String start_date12 = dates_split12[0];
        String end_date12 = dates_split12[1];
        query = "SELECT IF(staffID in (SELECT staffID FROM Editor),'editorial work', IF(staffID in (SELECT staffID FROM AuthorAssignedEdition), 'book authorship','article authorship')) AS workType, SUM(amount) as totalPayment FROM Payment NATURAL JOIN GetsPaid WHERE generatedDate BETWEEN '" + start_date12 + "' and '" + end_date12 + "' GROUP BY workType;";
      break;

      // to come out of the reports
      case 14:
        flag = false;
      break;

      default:
        System.out.println("Please enter a valid choice.");
      break;
    }

			if (query != null) {
				try {
					ResultSet rs = dbManager.executeQuery(query);
					ResultSetMetaData rsmd = rs.getMetaData();
					int numCol = rsmd.getColumnCount();
					System.out.println("Matching records:");
					while (rs.next()) {
						for (int i = 1; i <= numCol; i++) {
							if (rs.getString(i) != null)
                System.out.println(rsmd.getColumnName(i) + " : " + rs.getString(i));
              else
                System.out.println(rsmd.getColumnName(i) + " : 0");
						}
						System.out.println("");
					}
				} catch (Exception e) {
				e.printStackTrace();
				System.out.println("There was an error searching for the records. Please try again.");
				}
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