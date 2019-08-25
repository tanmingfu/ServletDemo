package com.tmf.servlet;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Enumeration;
import java.util.concurrent.Executor;
import java.lang.reflect.Field;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.StyledEditorKit.BoldAction;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

@WebServlet(urlPatterns = {"/StudentServlet"} ,name="studentServlet")
public class StudentServlet extends HttpServlet {


 
 @Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	// TODO Auto-generated method stub
	super.doGet(req, resp);
	System.out.println("DOGET");
}
 @Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		// TODO Auto-generated method stub
	 	req.setCharacterEncoding("UTF-8");
		System.out.println("DOPOS");
		Enumeration<String> enu =req.getParameterNames();

		try {
			Class cls =Class.forName("com.tmf.vo.Student");
			//ʵ��������
			Object obj= cls.getDeclaredConstructor(null).newInstance(null);
			while(enu.hasMoreElements()) {
				String ParameterName =enu.nextElement();
				System.out.println(ParameterName);
				System.out.println(req.getParameter(ParameterName));
				//˽������ֻ����������ȡ��
				Field f=cls.getDeclaredField(ParameterName);
				//��ȡ���Ե���������
				System.out.println(f.getType().getSimpleName());
				f.setAccessible(true);
				System.out.println(req.getParameter(ParameterName));
				//��������ֵ
				this.setValue(obj,f,req.getParameter(ParameterName));
				
			}
			System.out.println(obj.toString());
			req.setAttribute("stu", obj);
			req.getRequestDispatcher("/result.jsp").forward(req, resp);
			//resp.sendRedirect("/result.jsp");

			return ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	

	
 }
	public Boolean setValue(Object obj, Field f ,Object value) {
		
		try {
			String typeName =f.getType().getSimpleName();
		
			if(typeName.equalsIgnoreCase("String")) {				
					f.set(obj,String.valueOf(value));				
			}else if(typeName.equalsIgnoreCase("Byte")) {				
				f.setByte(obj,Byte.parseByte(String.valueOf(value)) )	;		
			}else if(typeName.equalsIgnoreCase("Integer")) {	//��װ��ֻ�ܴ����װ����,���ܴ���int 	��Integer.valueOf���ذ�װ����intValue����int����
				f.set(obj,Integer.valueOf(String.valueOf(value)));				
			}else if(typeName.equalsIgnoreCase("Boolean")) {				
				f.setBoolean(obj, Boolean.valueOf(String.valueOf(value)));			
			}else if(typeName.equalsIgnoreCase("Double")) {				
				f.setDouble(obj,Double.valueOf(String.valueOf(value)));				
			}
			//...������������д��
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;		
	}
}
