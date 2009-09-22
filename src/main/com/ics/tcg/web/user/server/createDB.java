package com.ics.tcg.web.user.server;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class createDB {

	/**
	 * @param args
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws IOException
	 */
	static java.sql.Statement stmt;

	public static void main(String[] args) throws SQLException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, IOException {

		java.sql.Connection conn;
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/project", "admin", "");
		stmt = conn.createStatement();
		
		createDB createdb=new createDB();
		createdb.insertUser();
		createdb.insertAbservice();
//		// createdb.insertCalendar();
//		createdb.insertRecords();

	}

	void insertUser() throws SQLException {

		stmt
				.executeUpdate("load data local infile 'd:/aa.txt' into table user");
		// load data local infile 'd:/aaa.txt' into table user

		for (int j = 0; j < 100; j++) {

			stmt.executeUpdate("update user set bymail="
					+ ((Math.random() * 3) > 1 ? true : false)
					+ " where userid=" + j);
			stmt.executeUpdate("update user set bymobile="
					+ ((Math.random() * 3) > 1 ? true : false)
					+ " where userid=" + j);
			stmt.executeUpdate("update user set sex="
					+ ((Math.random() * 2.5) > 1 ? true : false)
					+ " where userid=" + j);
		}
	}

	void insertRecords() throws SQLException {
		stmt.execute("insert into records values(1,1,1," + null + ",5,1,"
				+ true + ",1)");
		stmt.execute("insert into records values('2',1,'1'," + null + ",5,2,"
				+ true + ",1)");
		stmt.execute("insert into records values('3',1,'1'," + null + ",5,3,"
				+ true + ",1)");
		stmt.execute("insert into records values('4',1,'2'," + null + ",5,1,"
				+ false + ",1)");
		stmt.execute("insert into records values('5',1,'2'," + null + ",5,2,"
				+ false + ",1)");
		stmt.execute("insert into records values('6',1,'2'," + null + ",5,3,"
				+ true + ",1)");
	}

	void insertAbservice() throws SQLException {
		stmt
				.execute("insert into abstractservice values('1','BookAirTicketService','Book AirTicket')");
		stmt
				.execute("insert into abstractservice values('2','BookBusTicketService','Book BusTicket')");
		stmt
				.execute("insert into abstractservice values('3','BookTrainTicketService','Book TrainTicket')");
		stmt
				.execute("insert into abstractservice values('4','EmailService','EmailService')");
		stmt
				.execute("insert into abstractservice values('5','BookRoomService','RoomService')");
		stmt
				.execute("insert into abstractservice values('6','SMS','SMSService')");
		stmt
				.execute("insert into abstractservice values('7','WeatherForecastService','WeatherService')");
	}

	void insertCalendar() throws SQLException {
		for (int i = 1; i < 10; i++) {
			if (i % 2 == 0) {
				stmt.execute("insert into calendar values (" + i + ",'abc',"
						+ true + ",'2009-0" + i + "-00 10:00:00','name" + i
						+ "','2009-0" + i + "-00 00:00:00','2009-0" + i
						+ "-00 00:00:00',1)");
			} else {
				stmt.execute("insert into calendar values (" + i + ",'abc',"
						+ false + ",'2009-0" + i + "-00 00:00:00','name" + i
						+ "','2009-0" + i + "-00 00:00:00','2009-0" + i
						+ "-00 00:00:00',1)");

			}

		}
	}
	// stmt.executeUpdate("load data infile 'c:/a.txt' into table friends");

	// CalendarEvent calendarActivity = new CalendarEvent();
	// calendarActivity.setActivityname("aaa");
	// calendarActivity.setCalendarid(1);
	// calendarActivity.setDes("aaa");
	// calendarActivity.setEndtime(new Date());
	// calendarActivity.setStarttime(new Date());
	// calendarActivity.setUserid(1);
	// stmt.execute("insert into calendar values('"
	// + calendarActivity.getCalendarid() + "','"
	// + calendarActivity.getActivityname() + "','"
	// + calendarActivity.getDes() + "',"
	// + calendarActivity.getDone() + ",'"
	// + calendarActivity.getEndtime().toLocaleString() + "','"
	// + calendarActivity.getId() + "','"
	// + calendarActivity.getMasterId() + "','"
	// + calendarActivity.getObj() + "','"
	// + calendarActivity.getStarttime().toLocaleString() + "','"
	// + calendarActivity.getUserid() + "')");
	//		

	//		
	// InitialContext ctx;
	// Properties props = new Properties();
	// {
	// props.setProperty("java.naming.factory.initial",
	// "org.jnp.interfaces.NamingContextFactory");
	// props.setProperty("java.naming.provider.url", "localhost:1099");
	// props.setProperty("java.naming.factory.url.pkgs",
	// "org.jboss.naming");
	// }
	// try {
	// User_Service user_Service = new User_Service();
	// user_Service.setAbserviceid(4);
	// user_Service.setUserid(1);
	// user_Service.setId(4);
	// String aString = "aaa";
	// user_Service.setQos(aString.getBytes());
	//
	// ctx = new InitialContext(props);
	// TestDAO testDAO = (TestDAO) ctx
	// .lookup("TestDAOBean/remote");
	// testDAO.updateCalendar(user_Service);
	// System.out.println("aaa");
	//			 

	// for (int i = 0; i < calendars.size(); i++) {
	// DefaultEventData ded = new DefaultEventData();
	// CalendarEvent cal = calendars.get(i);
	// ded.setCalendarid(cal.getCalendarid());
	// ded.setUserid(cal.getUserid());
	// ded.setActivityname(cal.getActivityname());
	// ded.setStartTime(cal.getStarttime());
	// ded.setEndTime(cal.getEndtime());
	// ded.setData(cal.getDes());
	// ded.setIdentifier(cal.getId());
	// ded.setMasterId(cal.getMasterId());
	// ded.setDone(cal.getDone());
	// ded.setWorkflow((Client_Workflow) Byte_Object.ByteToObject(cal
	// .getObj()));
	// calendarsC.add(ded);
	// }

	// } catch (NamingException e) {
	// e.printStackTrace();
	// // } catch (IOException e) {
	// // e.printStackTrace();
	// }

	// PrintWriter out = new PrintWriter(new FileWriter("d:\\destnation.txt"));
	// BufferedReader in = new BufferedReader(new FileReader("d:\\a.txt"));

	// for (int i = 0; i < 100; i++) {
	// out.println(((Math.random()*2)>1?1:0));

	// //email
	// out.println(in.readLine()+"@mail.ics.com");
	//			
	// // age
	// out.println((int) (15.0 + Math.random() * 30.0));
	// // birthday
	// Integer integer = Integer.parseInt(in.readLine());
	// DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
	// Date date = new Date(110, 1, 1);
	// date.setYear(date.getYear() - integer);
	// date.setDate((int) (363.0 * Math.random()));
	// out.println(dateFormat.format(date));

	// out.close();

	// 把下面的字符串写入到文件的第一行.

	// out.println("the following is from source");
	// 下面的循环语句读入一行内容的同时进行写入.
	// }
}
