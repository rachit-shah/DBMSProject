import java.sql.*;
import java.util.ArrayList;
public class DBManager {
  private final String name = "rshah25";
  private final String pass = "polkadots";
  private final String jdbcURL = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/"+name;
  private Connection conn;
  public DBManager(){
    conn = getDBConnection();
  }
  private Connection getDBConnection() {
    Connection dbConnection;
    try {
        Class.forName("org.mariadb.jdbc.Driver");
        System.out.println("Driver connected");
    } catch (ClassNotFoundException e) {
        System.out.println(e.getMessage());
    }
    try {
        dbConnection = DriverManager.getConnection(jdbcURL, name, pass);
        System.out.println("Successfully Connected");
        return dbConnection;
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    return null;
  }

  public void startTransaction() throws SQLException{
    if(conn != null){
      conn.setAutoCommit(false);
    }
    else{
      System.err.println("DB Not Connected");
      throw new SQLException("DB Not Connected");
    }
  }
  public void rollbackTransaction() throws SQLException{
    if(conn != null){
      conn.rollback();
      conn.setAutoCommit(true);
    }
    else{
      System.err.println("DB Not Connected");
      throw new SQLException("DB Not Connected");
    }
  }
  public void commitTransaction() throws SQLException{
    if(conn != null){
      conn.commit();
      conn.setAutoCommit(true);
    }
    else{
      System.err.println("DB Not Connected");
      throw new SQLException("DB Not Connected");
    }
  }

  public void closeConnection(){
    close(conn);
  }

  public void close(Connection conn) {
    if(conn != null) {
      try { conn.close(); } catch(Throwable whatever) {}
    }
  }
  public void close(Statement st) {
    if(st != null) {
      try { st.close(); } catch(Throwable whatever) {}
    }
  }

  public void close(ResultSet rs) {
    if(rs != null) {
      try { rs.close(); } catch(Throwable whatever) {}
    }
  }
    public int executeUpdate(String query) throws SQLException{
    int count;
    boolean result;
    if(conn != null){
      Statement statement = conn.createStatement();
      result = statement.execute(query);
      count = statement.getUpdateCount();
      close(statement);
    }
    else{
      System.err.println("DB Not Connected");
      throw new SQLException("DB Not Connected");
    }
    return count;
  }
  public ArrayList<Integer> executeUpdateGetKeys(String query) throws SQLException{
    ArrayList<Integer> results = new ArrayList<Integer>();
    if(conn != null){
      Statement statement = conn.createStatement();
      statement.executeUpdate(query);
      ResultSet rs = statement.getGeneratedKeys();
      if(!rs.next()){
        System.out.println("No value changed");
      }
      else{
        do{
          results.add(rs.getInt(1));
        }
        while(rs.next());
      }
      close(statement);
      close(rs);
    }
    else{
      System.err.println("DB Not Connected");
      throw new SQLException("DB Not Connected");
    }
    return results;
  }
  public ResultSet executeQuery(String query) throws SQLException{
    if(conn != null){
      Statement statement = conn.createStatement();
      ResultSet rs = statement.executeQuery(query);
      close(statement);
      return rs;
    }
    else{
      System.err.println("DB Not Connected");
      return null;
    }
  }
  

  //Create Database
  public void init() throws SQLException {
    //Create Statements
    String createStaff = "CREATE TABLE IF NOT EXISTS Staff ("+
                            "staffID INT PRIMARY KEY,"+
                            "name VARCHAR(30),"+
                            "age INT,"+
                            "email VARCHAR(30),"+
                            "phone VARCHAR(10),"+
                            "gender CHAR(1) CHECK (gender in ('M','F')),"+
                            "address VARCHAR(100),"+
                            "joinDate DATE NOT NULL,"+
                            "username VARCHAR(30) UNIQUE NOT NULL,"+
                            "password VARCHAR(50) NOT NULL,"+
                            "salary INT"+
                          ")";

    String createEditor = "CREATE TABLE IF NOT EXISTS Editor (staffID INT PRIMARY KEY, type CHAR(8) CHECK (type in ('internal','external')), FOREIGN KEY (staffID) REFERENCES Staff(staffID) ON UPDATE CASCADE ON DELETE CASCADE)";

    String createAuthor = "CREATE TABLE IF NOT EXISTS Author (staffID INT PRIMARY KEY, type CHAR(8) CHECK (type in ('internal','external')), FOREIGN KEY (staffID) REFERENCES Staff(staffID) ON UPDATE CASCADE ON DELETE CASCADE)";
    
    String createPubMgr = "CREATE TABLE IF NOT EXISTS PublicationManager (staffID INT PRIMARY KEY, FOREIGN KEY (staffID) REFERENCES Staff(staffID) ON UPDATE CASCADE ON DELETE CASCADE)";

    String createDistMgr = "CREATE TABLE IF NOT EXISTS DistributionManager ( staffID INT PRIMARY KEY, FOREIGN KEY (staffID) REFERENCES Staff(staffID) ON UPDATE CASCADE ON DELETE CASCADE)";

    String createDistributors = "CREATE TABLE IF NOT EXISTS Distributors (distID INT PRIMARY KEY, name varchar(50), contactPerson varchar(50) NOT NULL, type varchar(20) NOT NULL, strAddr varchar(30) NOT NULL, city varchar(10) NOT NULL, phone char(10) NOT NULL UNIQUE, balance float NOT NULL, UNIQUE (strAddr, city))";

    String createPayment = "CREATE TABLE IF NOT EXISTS Payment (payID INT PRIMARY KEY, generatedDate DATE NOT NULL, amount INT NOT NULL, claimedDate DATE)";

    String createGetsPaid = "CREATE TABLE IF NOT EXISTS GetsPaid ( staffID INT, payID INT, PRIMARY KEY (staffID, payID), FOREIGN KEY (staffID) REFERENCES Staff(staffID) ON UPDATE CASCADE ON DELETE CASCADE, FOREIGN KEY (payID) REFERENCES Payment(payID) ON UPDATE CASCADE ON DELETE CASCADE)";

    String createDistGetsPayment = "CREATE TABLE IF NOT EXISTS DistGetsPayment(distID INT, payID INT, PRIMARY KEY(distID, payID), FOREIGN KEY(distID) REFERENCES Distributors(distID) ON UPDATE CASCADE ON DELETE CASCADE, FOREIGN KEY(payID) REFERENCES Payment(payID) ON UPDATE CASCADE ON DELETE CASCADE)";

    String createPublication = "CREATE TABLE IF NOT EXISTS Publication(pubID INT,title VARCHAR(100) NOT NULL,PRIMARY KEY(pubID))";
    
    String createBooks = "CREATE TABLE IF NOT EXISTS Books (pubID INT,topic VARCHAR(100) NOT NULL,PRIMARY KEY(pubID),FOREIGN KEY(pubID) REFERENCES Publication(pubID) ON UPDATE CASCADE ON DELETE CASCADE)";
    
    String createMagazines = "CREATE TABLE IF NOT EXISTS Magazines (pubID INT,periodicity VARCHAR(100) NOT NULL,PRIMARY KEY(pubID),FOREIGN KEY(pubID) REFERENCES Publication(pubID) ON UPDATE CASCADE ON DELETE CASCADE)";

    String createJournals = "CREATE TABLE IF NOT EXISTS Journals (pubID INT,periodicity VARCHAR(100) NOT NULL,PRIMARY KEY(pubID),FOREIGN KEY(pubID) REFERENCES Publication(pubID) ON UPDATE CASCADE ON DELETE CASCADE)";

    String createIssue = "CREATE TABLE IF NOT EXISTS Issue (issueID INT,pubID INT,date DATE,tabOfCont VARCHAR(1000),PRIMARY KEY(issueID, pubID),FOREIGN KEY(pubID) REFERENCES Publication(pubID) ON UPDATE CASCADE ON DELETE CASCADE)";
    
    String createEdition = "CREATE TABLE IF NOT EXISTS Edition (editionID INT,pubID INT,ISBN CHAR(30) NOT NULL,date DATE NOT NULL,tabOfCont VARCHAR(100),UNIQUE(ISBN),PRIMARY KEY(editionID, pubID),FOREIGN KEY(pubID) REFERENCES Publication(pubID) ON UPDATE CASCADE ON DELETE CASCADE)";
    
    String createArticle = "CREATE TABLE IF NOT EXISTS Article (artID INT,topic VARCHAR(20) NOT NULL,title VARCHAR(100),date DATE,text VARCHAR(200),PRIMARY KEY(artID))";

    String createChapter = "CREATE TABLE IF NOT EXISTS Chapter (chapID INT PRIMARY KEY,title varchar(100) NOT NULL,date DATE,text varchar(1000))";
    
    String createIssueContains = "CREATE TABLE IF NOT EXISTS IssueContains (pubID INT,issueID INT,artID INT,artNum INT NOT NULL,PRIMARY KEY (pubID, issueID, artID),FOREIGN KEY (pubID) REFERENCES Publication(pubID) ON UPDATE CASCADE ON DELETE CASCADE, FOREIGN KEY(issueID) REFERENCES Issue(issueID) ON UPDATE CASCADE ON DELETE CASCADE,FOREIGN KEY(artID) REFERENCES Article(artID) ON UPDATE CASCADE ON DELETE CASCADE)";
    
    String createEditionContains = "CREATE TABLE IF NOT EXISTS EditionContains (pubID INT,editionID INT,chapID INT,chapNum INT NOT NULL,PRIMARY KEY (pubID, editionID, chapID),FOREIGN KEY (pubID) REFERENCES Publication(pubID) ON UPDATE CASCADE ON DELETE CASCADE, FOREIGN KEY(editionID) REFERENCES Edition(editionID) ON UPDATE CASCADE ON DELETE CASCADE, FOREIGN KEY(chapID) REFERENCES Chapter(chapID) ON UPDATE CASCADE ON DELETE CASCADE)";
    
    String createOrders = "CREATE TABLE IF NOT EXISTS Orders (orderID INT PRIMARY KEY, orderDate DATE NOT NULL, deliveryDate DATE NOT NULL, price float NOT NULL, numCopies INT NOT NULL, shipCost float NOT NULL)";

    String createdistAssigned = "CREATE TABLE IF NOT EXISTS DistAssigned (distID INT, orderID INT, PRIMARY KEY(distID, orderID), FOREIGN KEY(distID) REFERENCES Distributors(distID) ON UPDATE CASCADE ON DELETE CASCADE, FOREIGN KEY(orderID) REFERENCES Orders(orderID) ON UPDATE CASCADE ON DELETE CASCADE)";
    
    String createPymtConnToOrder = "CREATE TABLE IF NOT EXISTS PaymentConnectedToOrder (payID INT, orderID INT, PRIMARY KEY(payID, orderID), FOREIGN KEY(payID) REFERENCES Payment(payID) ON UPDATE CASCADE ON DELETE CASCADE, FOREIGN KEY(orderID) REFERENCES Orders(orderID) ON UPDATE CASCADE ON DELETE CASCADE)";
    
    String createOrderHasIssue = "CREATE TABLE IF NOT EXISTS OrderHasIssue (orderID INT, pubID INT, issueID INT, PRIMARY KEY(pubID , orderID, issueID), FOREIGN KEY(orderID) REFERENCES Orders(orderID) ON UPDATE CASCADE ON DELETE CASCADE, FOREIGN KEY(pubID) REFERENCES Issue(pubID) ON UPDATE CASCADE ON DELETE CASCADE, FOREIGN KEY(issueID) REFERENCES Issue(issueID) ON UPDATE CASCADE ON DELETE CASCADE)";
    
    String createOrderHasEdition = "CREATE TABLE IF NOT EXISTS OrderHasEdition  (orderID INT, pubID INT, editionID INT, PRIMARY KEY(orderID, pubID, editionID), FOREIGN KEY(orderID) REFERENCES Orders(orderID) ON UPDATE CASCADE ON DELETE CASCADE, FOREIGN KEY(pubID) REFERENCES Edition(pubID) ON UPDATE CASCADE ON DELETE CASCADE, FOREIGN KEY(editionID) REFERENCES Edition(editionID) ON UPDATE CASCADE ON DELETE CASCADE)";

    String createDistMgrCreateUpdate = "CREATE TABLE IF NOT EXISTS  DistMngCreateUpdate(staffID INT, distID INT, PRIMARY KEY(staffID, distID), FOREIGN KEY(staffID) REFERENCES Staff(staffID) ON UPDATE CASCADE ON DELETE CASCADE, FOREIGN KEY(distID) REFERENCES Distributors(distID) ON UPDATE CASCADE ON DELETE CASCADE)";

    String createPubMgrCreateUpdate = "CREATE TABLE IF NOT EXISTS PubMngCreateUpdate(staffID INT, pubID INT, PRIMARY KEY(staffID, pubID), FOREIGN KEY(staffID) REFERENCES Staff(staffID) ON UPDATE CASCADE ON DELETE CASCADE, FOREIGN KEY(pubID) REFERENCES Publication(pubID) ON UPDATE CASCADE ON DELETE CASCADE)";

    String createEnter = "CREATE TABLE IF NOT EXISTS Enter(staffID INT, payID INT, PRIMARY KEY(staffID, payID), FOREIGN KEY(staffID) REFERENCES Staff(staffID) ON UPDATE CASCADE ON DELETE CASCADE, FOREIGN KEY(payID) REFERENCES Payment(payID) ON UPDATE CASCADE ON DELETE CASCADE)";

    String createEditorAssigned = "CREATE TABLE IF NOT EXISTS EditorAssigned (staffID INT, pubID INT, PRIMARY KEY(staffID, pubID), FOREIGN KEY(staffID) REFERENCES Editor(staffID) ON UPDATE CASCADE ON DELETE CASCADE, FOREIGN KEY(pubID) REFERENCES Publication(pubID) ON UPDATE CASCADE ON DELETE CASCADE)";

    String createAuthAssignedArticle = "CREATE TABLE IF NOT EXISTS AuthorAssignedArticle (staffID INT, artID INT, PRIMARY KEY(staffID, artID), FOREIGN KEY(staffID) REFERENCES Staff(staffID) ON UPDATE CASCADE ON DELETE CASCADE, FOREIGN KEY(artID) REFERENCES Article(artID) ON UPDATE CASCADE ON DELETE CASCADE)";

    String createAuthAssignedEdition = "CREATE TABLE IF NOT EXISTS AuthorAssignedEdition( staffID INT, editionID INT, pubID INT, PRIMARY KEY(staffID, editionID, pubID), FOREIGN KEY(staffID) REFERENCES Staff(staffID) ON UPDATE CASCADE ON DELETE CASCADE, FOREIGN KEY(editionID) REFERENCES Edition(editionID) ON UPDATE CASCADE ON DELETE CASCADE, FOREIGN KEY(pubID) REFERENCES Edition(pubID) ON UPDATE CASCADE ON DELETE CASCADE)";
    
    String createBill = "CREATE TABLE IF NOT EXISTS Bill (billID INT PRIMARY KEY AUTO_INCREMENT, billAmt FLOAT NOT NULL, billDetails VARCHAR(1000) NOT NULL)";

    String createDistGetsBill = "CREATE TABLE IF NOT EXISTS DistGetsBill(distID INT, billID INT, PRIMARY KEY(distID, billID), FOREIGN KEY(distID) REFERENCES Distributors(distID) ON UPDATE CASCADE ON DELETE CASCADE, FOREIGN KEY(billID) REFERENCES Bill(billID) ON UPDATE CASCADE ON DELETE CASCADE)";

    String createBillConnToOrder = "CREATE TABLE IF NOT EXISTS BillConnectedToOrder (billID INT, orderID INT, PRIMARY KEY(billID, orderID), FOREIGN KEY(billID) REFERENCES Bill(billID) ON UPDATE CASCADE ON DELETE CASCADE, FOREIGN KEY(orderID) REFERENCES Orders(orderID) ON UPDATE CASCADE ON DELETE CASCADE)";

    //Execute Statements
    try{
      startTransaction();
      executeUpdate("USE "+name+";");
      executeUpdate(createStaff);
      executeUpdate(createEditor);
      executeUpdate(createAuthor);
      executeUpdate(createPubMgr);
      executeUpdate(createDistMgr);
      executeUpdate(createDistributors);
      executeUpdate(createPayment);
      executeUpdate(createGetsPaid);
      executeUpdate(createDistGetsPayment);
      executeUpdate(createPublication);
      executeUpdate(createBooks);
      executeUpdate(createMagazines);
      executeUpdate(createJournals);
      executeUpdate(createIssue);
      executeUpdate(createEdition);
      executeUpdate(createArticle);
      executeUpdate(createChapter);
      executeUpdate(createIssueContains);
      executeUpdate(createEditionContains);

      executeUpdate(createOrders);
      executeUpdate(createdistAssigned);
      executeUpdate(createPymtConnToOrder);
      executeUpdate(createOrderHasIssue);
      executeUpdate(createOrderHasEdition);
      executeUpdate(createDistMgrCreateUpdate);
      executeUpdate(createPubMgrCreateUpdate);
      executeUpdate(createEnter);
      executeUpdate(createEditorAssigned);
      executeUpdate(createAuthAssignedArticle);
      executeUpdate(createAuthAssignedEdition);

      executeUpdate(createBill);
      executeUpdate(createDistGetsBill);
      executeUpdate(createBillConnToOrder);
      
  
      commitTransaction();
      System.out.println("Tables created successfully");
    } 
    catch (Exception e) {
      rollbackTransaction();
      e.printStackTrace();
      System.out.println("Error when creating tables");
      throw new SQLException("Error when creating tables");
    }

  }

  public void insertSampleData() throws SQLException{
    //Insert Statements
    String insertStaff = "INSERT INTO Staff(staffID,joinDate,username,password,salary) VALUES (1,\"2020-01-10\",\"Mark\",\"Yr5eE-v\",23000), (2,\"2019-06-20\",\"Rob\",\"&jtPp5^\",NULL), (3,\"2017-01-30\",\"Kathy\",\"BT@4m+D\",26000), (4,\"2020-02-12\",\"Carole\",\"cwu#V9J\",NULL), (5,\"2018-08-30\",\"Clark\",\"f5%N9m4\",32000), (6,\"2016-09-12\",\"Veronica\",\"!Ue3%62\",33000), (7,\"2019-04-11\",\"Brian\",\"r^UD7Wg\",30000), (8,\"2015-11-22\",\"Michael\",\"mZ@2#us\",50000), (9,\"2016-10-02\",\"Ron\",\"@us*pk2\",44000), (10,\"2017-08-01\",\"Sam\",\"#k9*1ep\",24000), (11,\"2018-05-21\",\"Samuel\",\"ms$1w!t2\",26000), (12,\"2018-06-12\",\"Monica\",\"k3g#uk2\",31000), (13,\"2016-07-30\",\"Kelly\",\"t2@5m!k\",20000), (14,\"2015-10-14\",\"Shaun\",\"o0&2w_a\",16000), (15,\"2015-09-27\",\"Ashton\",\"j$23k!wq\",34000), (16,\"2017-04-17\",\"Ashley\",\"q1#3e@m*\",23000), (17,\"2014-08-23\",\"Elon\",\"m*d@1qz$\",25000)";  
  

    String insertEditor="INSERT INTO Editor VALUES (1, \"internal\"), (2, \"external\"), (9, \"internal\"), (10, \"external\")";
    
    String insertAuthor="INSERT INTO Author VALUES (3, \"internal\"),(4, \"external\"),(11, \"internal\"),(12, \"external\")";

    String insertPubMgr = "INSERT INTO PublicationManager VALUES (5),(6),(13),(14)";
    
    String insertDistMgr = "INSERT INTO DistributionManager VALUES (7),(8),(15),(16)";

    String insertDistributors = "INSERT INTO Distributors (distID, name, contactPerson, type, strAddr, city, phone, balance) VALUES (1,\"Gardners Books\",\"Elizabeth\",\"wholesale\",\"110 Levon St\",\"Raleigh\",\"9199902323\",0.0),(2,\"Thomas Distributors\",\"Nathon\",\"library\",\"3 Aberdeen Dr\",\"Chicago\",\"7732027890\",2000.0),(3,\"CBL Distribution Ltd\", \"Samantha\",\"library\",\"41 Kathe Lane\",\"New York\",\"6311330908\",1500.0),(4,\"Combined Book Services Limited\", \"Kearny\",\"wholesale\",\"55 Lidon Dr\",\"Miami\",\"7865067444\",3000.0)";

    String insertPayment = "INSERT INTO Payment (payID, generatedDate, amount, claimedDate) VALUES (1,\"2018-02-10\",23000,\"2018-02-16\"),(2,\"2017-07-15\",12000,\"2017-07-18\"),(3,\"2019-12-17\",20000,\"2019-12-21\"),(4,\"2020-03-01\",80000,NULL),(5,\"2018-08-27\",26000,\"2018-08-31\"),(6,\"2020-02-11\",195000,NULL),(7,\"2017-03-08\",19000,\"2017-03-12\"),(8,\"2015-02-01\",32000,\"2015-02-11\"),(9,\"2019-09-19\",33000,\"2019-09-24\"),(10,\"2020-01-30\",24000,NULL),(11,\"2019-06-14\",30000,\"2019-06-20\"),(12,\"2017-08-12\",50000,\"2017-08-19\"),(13,\"2016-12-07\",44000,\"2016-12-12\"),(14,\"2020-02-19\",24000,NULL),(15,\"2020-02-10\",26000,NULL),(16,\"2020-02-10\",31000,NULL),(17,\"2020-02-10\",20000,\"2020-02-15\"),(18,\"2020-02-10\",16000,NULL),(19,\"2020-02-10\",34000,\"2020-02-19\"),(20,\"2020-02-10\",23000,NULL),(21,\"2020-02-10\",25000,NULL)";

    String insertGetsPaid = "INSERT INTO GetsPaid (payID,staffID) VALUES (1,1),(3,2),(5,3),(7,4),(8,5),(9,6),(11,7),(12,8),(13,9),(14,10),(15,11),(16,12),(17,13),(18,14),(19,15),(20,16),(21,17)";

    String insertDistGetsPayment = "INSERT INTO DistGetsPayment (payID,distID) VALUES (2,4),(4,1),(6,2),(10,3)";
    
    String insertPublication = "INSERT INTO Publication(pubID,title) VALUES (1,\"Uncanny Valley\"),(2,\"The Glass Hotel\"),(3, \"Long Bright River\"),(4, \"The Authenticity Project\"),(5, \"Nature Communications\"),(6, \"The New Yorker\"),(7, \"Vogue\"),(8, \"The Atlantic\"),(9, \"Journal of Agromedicine\"),(10, \"Philosophical Transactions of the Royal Society A\"),(11, \"PLOS ONE\"),(12, \"Proceedings of the National Academy of Sciences\")";

    String insertBooks = "INSERT INTO Books(pubID,topic) VALUES(1, \"biography\"),(2, \"mystery novel\"),(3, \"mystery novel\"),(4, \"literary fiction\")";

    String insertMagazines = "INSERT INTO Magazines(pubID,periodicity) VALUES (5, \"Weekly\"),(6, \"Monthly\"),(7, \"Yearly\"),(8, \"Weekly\")";

    String insertJournals = "INSERT INTO Journals(pubID,periodicity) VALUES (9, \"Weekly\"),(10, \"Monthly\"),(11, \"Yearly\"),(12, \"Weekly\")";

    String insertIssue = "INSERT INTO Issue (issueID, pubID, date,tabOfCont) VALUES (1,5,\"2019-12-12\",\"Article 1: Alloys of platinum and early transition metals as oxygen reduction electrocatalysts\"),(2,5,\"2019-12-12\",\"Article 1: Selling out on nature Article 2: The nature of the hydrated excess proton in water\"),(1,7,\"2017-04-16\",NULL),(2,8,\"2020-06-18\",NULL),(1,9,\"2014-06-11\",\"Article 1: Silo-Filler Disease: One Health System Experience and an Update of the Literature\"),(2,9,\"2019-04-21\",\"Article 1: A Systematic Review of Large Agriculture Vehicles Use and Crash Incidents on Public Roads\"),(1,10,\"2018-11-20\",NULL),(1,11,\"2016-07-30\",NULL)";

    String insertArticle = "INSERT INTO Article (artID, topic, title,date, text) VALUES(1,\"agroscience\",\"Silo-Filler's Disease\",\"2020-01-30\",\"Silo-filler's disease, a life-threatening condition from exposure to silage.\"),(2,\"agroscience\",\" Review of Large Agriculture\",\"2016-11-20\",\"Agricultural vehicles are a common sight on rural public roads.\"),(3,\"nature\",\"Alloys of platinum\",\"2017-01-12\",\"The widespread use of low-temperature polymer electrolyte membrane fuel.\"),(4,\"nature\",\"Selling out on nature\",\"2016-09-30\",\"With scant evidence that market-based conservation works, argues Douglas J. McCauley.\"),(5,\"nature\",\"The nature of the hydrated excess proton in water\",\"2016-09-30\",\"Explanations for the anomalously high mobility of protons in liquid water began with Grotthuss's\")";
    
    String insertChapter = "INSERT INTO Chapter(chapID, title, date, text) VALUES (1, \"New Morning\", \"2020-01-20\", \"It is a beautiful morning.\"), (2, \"Realization\", \"2020-01-20\", \"Whoops!. I slept on the wrong side.\"), (3, \"Waking up\", \"2020-01-20\", \"I should get up now or I'll be late to the class.\"), (4, \"Good news\", \"2020-01-20\", \"But it's spring break! So, I can sleep!\"), (5, \"End\", \"2020-01-20\", \"The END\"), (6, \"Foreword\",\"2020-01-20\", \"I dedicate this to my husband Ed and my two kids Edd and Eddy.\")";

    String insertIssueContains = "INSERT INTO IssueContains (pubID, issueID, artID, artNum) VALUES (9,1,1,1), (9,2,2,1), (5,1,3,1), (5,2,4,1), (5,2,5,2)";

    String insertEdition = "INSERT INTO Edition (editionId, pubID, ISBN, date, tabOfCont) VALUES (1,1,\"978-3-16-148410-0\",\"2020-01-20\",NULL),(1,2,\"978-4-16-148410-0\",\"2020-01-20\",\"Chapter 1: Foreword\"),(1,3,\"978-5-16-148410-0\",\"2020-01-20\",\"Chapter 1: New Morning Chapter 2: Realization\"),(1,4,\"978-6-16-148410-0\",\"2020-01-20\",\"Chapter 1: Waking up Chapter 2: Good news\"),(2,4,\"978-7-16-148410-0\",\"2020-01-20\",\"Chapter 1: End\")";



    String insertEditionContains = "INSERT INTO EditionContains (pubID, editionID,chapID, chapNum) VALUES (2, 1, 6, 1), (3, 1, 1, 1), (3, 1, 2, 2), (4, 1, 3, 1), (4, 1, 4, 2), (4, 2, 5, 1)";

    String insertOrders = "INSERT INTO Orders(orderID, orderDate, deliveryDate, price, numCopies, shipCost) VALUES (1,\"2018-11-09\",\"2018-11-09\", 4000,20,500), (2,\"2017-10-30\",\"2018-11-09\",1200,10,100), (3,\"2019-01-12\",\"2018-11-09\",13000,15,400), (4,\"2017-09-20\",\"2018-11-09\",8000,30,600), (5,\"2018-01-19\",\"2018-11-09\", 3000,20,400), (6,\"2017-11-13\",\"2018-11-09\",10000,20,200), (7,\"2019-07-11\",\"2018-11-09\",9000,25,300), (8,\"2018-08-21\",\"2018-11-09\",6000,35,500)";

    String insertdistAssigned = "INSERT INTO DistAssigned (distID, orderID) VALUES (1,1),(2,3),(3,4),(4,2),(1,8),(2,6),(3,7),(4,5)";
    
    String insertPymtConnToOrder = "INSERT INTO PaymentConnectedToOrder (payID, orderID) VALUES (2,2),(4,1),(6,3),(10,4)";
    
    String insertOrderHasIssue = "INSERT INTO OrderHasIssue(orderID, issueID,  pubID) VALUES (1,1,5),(2,2,9),(3,1,7),(4,2,5)";
    
    String insertOrderHasEdition="INSERT INTO OrderHasEdition(orderID, editionID, pubID) VALUES (5,1,1),(6,1,3),(7,2,4),(8,1,4)";
    
    String insertDistMgrCreateUpdate="INSERT INTO DistMngCreateUpdate(staffID, distID) VALUES (7,1),(8,2),(15,3),(16,4)";
    
    String insertPubMgrCreateUpdate="INSERT INTO PubMngCreateUpdate(staffID, pubID) VALUES (5,1),(6,2),(13,3),(14,4)";
    
    String insertEnter="INSERT INTO Enter(staffID, payID) VALUES (1,1),(3,2),(2,3),(3,5)";
    
    String insertEditorAssigned="INSERT INTO EditorAssigned(staffID, pubID) VALUES (1,1),(2,5),(9,4),(10,9)";

    String insertAuthAssignedArticle = "INSERT INTO AuthorAssignedArticle(staffID, artID) VALUES (3,1),(4,2),(11,3),(12,4)";
  
    String insertAuthAssignedEdition = "INSERT INTO AuthorAssignedEdition(staffID,editionID,pubID) VALUES (3,1,1),(4,1,2),(11,1,3),(12,1,4)";

    //Execute Statements
    try{
      startTransaction();
      executeUpdate("USE "+name+";");
      executeUpdate(insertStaff);
      executeUpdate(insertEditor);
      executeUpdate(insertAuthor);
      executeUpdate(insertPubMgr);
      executeUpdate(insertDistMgr);
      executeUpdate(insertDistributors);
      executeUpdate(insertPayment);
      executeUpdate(insertGetsPaid);
      executeUpdate(insertDistGetsPayment);
      executeUpdate(insertPublication);
      executeUpdate(insertBooks);
      executeUpdate(insertMagazines);
      executeUpdate(insertJournals);
      executeUpdate(insertIssue);
      executeUpdate(insertEdition);
      executeUpdate(insertArticle);
      executeUpdate(insertChapter);
      executeUpdate(insertIssueContains);
      executeUpdate(insertEditionContains);

      executeUpdate(insertOrders);
      executeUpdate(insertdistAssigned);
      executeUpdate(insertPymtConnToOrder);
      executeUpdate(insertOrderHasIssue);
      executeUpdate(insertOrderHasEdition);
      executeUpdate(insertDistMgrCreateUpdate);
      executeUpdate(insertPubMgrCreateUpdate);
      executeUpdate(insertEnter);
      executeUpdate(insertEditorAssigned);
      executeUpdate(insertAuthAssignedArticle);
      executeUpdate(insertAuthAssignedEdition);
      
  
      commitTransaction();
      System.out.println("Data inserted successfully");
    } 
    catch (Exception e) {
      rollbackTransaction();
      System.out.println("Error when adding sample data");
    }
  }
  public void insertUsers(){
    String query = null;
    try{
      //Insert Admin
      startTransaction();
      query = "INSERT INTO Staff(staffID,joinDate,username,password) VALUES (9991,NOW(),\"admin\",\"admin\")"; 
      executeUpdate(query);
      commitTransaction(); 

      //Insert Publication Manager
      startTransaction();
      query = "INSERT INTO Staff(staffID,joinDate,username,password) VALUES (9992,NOW(),\"pub\",\"pub\")"; 
      executeUpdate(query);
      query = "INSERT INTO PublicationManager(staffID) VALUES (9992)";
      executeUpdate(query);
      commitTransaction(); 

      //Insert Distribution Manager
      startTransaction();
      query = "INSERT INTO Staff(staffID,joinDate,username,password) VALUES (9993,NOW(),\"dist\",\"dist\")"; 
      executeUpdate(query);
      query = "INSERT INTO DistributionManager(staffID) VALUES (9993)";
      executeUpdate(query);
      commitTransaction(); 

      //Insert Editor 
      startTransaction();
      query = "INSERT INTO Staff(staffID,joinDate,username,password) VALUES (9994,NOW(),\"editor\",\"editor\")"; 
      executeUpdate(query);
      query = "INSERT INTO Editor(staffID,type) VALUES (9994,\"internal\")";
      executeUpdate(query);
      commitTransaction(); 

      //Insert Author
      startTransaction();
      query = "INSERT INTO Staff(staffID,joinDate,username,password) VALUES (9995,NOW(),\"author\",\"author\")"; 
      executeUpdate(query);
      query = "INSERT INTO Author(staffID,type) VALUES (9995,\"internal\")";
      executeUpdate(query);
      commitTransaction(); 
    }
    catch(Exception e){
      try{
      rollbackTransaction();
      }
      catch(Exception ex){

      }
    }
  }
}