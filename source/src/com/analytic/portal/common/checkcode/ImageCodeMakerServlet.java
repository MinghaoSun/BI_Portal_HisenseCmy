package com.analytic.portal.common.checkcode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImageCodeMakerServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8720647251896136772L;
	// 随即产生的字符串 
	private static final char[] CODE_CHARS= "ABCDEFGHJKLMNPQRTUVWXYabcdefghijkmnpqrstuvwxy23456789".toCharArray(); 
	private static final int IMG_W = 80;
	private static final int IMG_H = 40;
	private static final Random RANDOM = new Random();


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置响应头 Content-type类型  
        response.setContentType("image/jpeg");  
        // 以下三句是用于设置页面不缓存  
        response.setHeader("Pragma", "No-cache");  
        response.setHeader("Cache-Control", "No-cache");  
        response.setDateHeader("Expires", 0);  
  
        OutputStream os = response.getOutputStream();  
        // 建立指定宽、高和BufferedImage对象  
        BufferedImage image = new BufferedImage(IMG_W, IMG_H,BufferedImage.TYPE_INT_RGB);  
        Graphics2D g = image.createGraphics(); // 该画笔画在image上  
        g.setFont(new Font("黑体", Font.BOLD, 24));  
        Color c = g.getColor(); // 保存当前画笔的颜色，用完画笔后要回复现场  
        g.fillRect(0, 0, IMG_W, IMG_H);  
        g.setColor(getRandomColor(200, 250));
        drawRandomLines(g, 160);
        g.setColor(getRandomColor(180, 200));
        StringBuffer sRand = new StringBuffer(); // 保存随即产生的字符串  
        
        for (int i = 0; i < 4; i++) {  
            // 设置字体  
            // 随即生成
            String rand = new Character(CODE_CHARS[RANDOM.nextInt(CODE_CHARS.length)]).toString();  
            sRand.append(rand);
            Color color = new Color(20 + RANDOM.nextInt(20), 
					20 + RANDOM.nextInt(20), 20 + RANDOM.nextInt(20));
            g.setColor(color);
            AffineTransform trans = new AffineTransform();
			trans.rotate(RANDOM.nextInt(45) * 3.14 / 180, 15 * i + 8, 7);
			// 缩放文字
			float scaleSize = RANDOM.nextFloat() + 0.8f;
			if (scaleSize > 1f)
				scaleSize = 1f;
			trans.scale(scaleSize, scaleSize);
			g.setTransform(trans);
            g.drawString(rand, 20 * i + 6, 25);
        }  
        g.setColor(c); // 将画笔的颜色再设置回去 
        g.dispose();  
        //将验证码记录到session
        request.getSession().setAttribute("rand", sRand.toString());
        // 输出图像到页面  
        ImageIO.write(image, "JPEG", os);  
	}
	
	public Color getRandomColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 200;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
	
	public void drawRandomLines(Graphics g, int nums) {
		g.setColor(this.getRandomColor(160, 200));
		for (int i = 0; i < nums; i++) {
			int x1 = RANDOM.nextInt(IMG_W);
			int y1 = RANDOM.nextInt(IMG_H);
			int x2 = RANDOM.nextInt(12);
			int y2 = RANDOM.nextInt(12);
			g.drawLine(x1, y1, x2, y2);
		}
	}
	
}
