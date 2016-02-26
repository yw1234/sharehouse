package util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckCode extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	protected int snowNumber = 15;

	public CheckCode() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");

		int width = 100;
		int height = 25;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		
		Random random = new Random();
		g.fillRect(0, 0, width, height);
		image = ((Graphics2D) g).getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT); 
		g.dispose(); 
		g = image.createGraphics(); 
		String sRand = "";
		Font mFont = new Font("",Font.ITALIC,24);
		g.setFont(mFont);
		int itmp = 0;
		for(int i = 0; i < 5 ; i++)
		{
			g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
			if(random.nextInt(3) == 0)
			{
				itmp = random.nextInt(26) + 65;
			}
			else if(random.nextInt(3) == 1)
			{
				itmp = random.nextInt(10) + 48;
			}
			else{
				itmp = random.nextInt(10) + 97;
			}
			char ctmp = (char) itmp;
			sRand += String.valueOf(ctmp);
			Graphics2D g2d_word = (Graphics2D) g;
			AffineTransform trans = new AffineTransform();
			float scaleSize = random.nextFloat() + 1.0f;
			if(scaleSize < 0.8f || scaleSize > 1.2f)
			{
				scaleSize = 1f;
			}
			trans.scale(scaleSize, scaleSize);
			g2d_word.setTransform(trans);
			g.drawString(String.valueOf(ctmp), width/6*i+8, random.nextInt(height/5)+17);
		}
		HttpSession session = request.getSession();
		session.setAttribute("CheckCode", sRand);
		g.dispose();
		ImageIO.write(image, "PNG", response.getOutputStream());
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
