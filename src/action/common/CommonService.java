package action.common;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import model.Circle;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import template.CommonBlockTemplate;
import util.GetAC;

import com.opensymphony.xwork2.ActionSupport;

import dao.user.UserGetDao;

public class CommonService extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String str;
	private String encodeType;
	private String type;
	private String province;
	private Long id;
	protected HttpServletResponse response = ServletActionContext.getResponse();
	@SuppressWarnings("deprecation")
	public String place() throws IOException{
		JSONObject root = new JSONObject();
		try{
			String fileURL = ServletActionContext.getRequest().getRealPath("/WEB-INF/zone.xml");
			File file = new File(fileURL);
			Document document = null;
			Element country = null;
			String result = "";
			if(file.exists())
			{
				SAXReader reader = new SAXReader();
				try{
					document = reader.read(file);
					country = document.getRootElement();
					if(type.equals("province")){
						List<Element>provinceList = country.elements("province");
						Element provinceElement = null;
						for(int i = 0 ; i < provinceList.size();i++)
						{
							provinceElement = provinceList.get(i);
							result += provinceElement.attributeValue("name")+",";
						}
						result.substring(0,result.length()-1);
					}else if(type.equals("city")){
						String selProvince = ServletActionContext.getRequest().getParameter("province");
						selProvince = java.net.URLDecoder.decode(selProvince,"UTF-8");
						Element item = (Element) country.selectSingleNode("/country/province[@name='" +selProvince+ "']");
						List<Element>cityList = item.elements("city");
						Element cityElement = null;
						for(int i = 0 ; i < cityList.size();i++)
						{
							cityElement = cityList.get(i);
							result += cityElement.attributeValue("name")+",";
						}
						result.substring(0,result.length()-1);
					}else if(type.equals("area")){
						String selProvince = ServletActionContext.getRequest().getParameter("province");
						String selCity = ServletActionContext.getRequest().getParameter("city");
						selProvince = java.net.URLDecoder.decode(selProvince,"UTF-8");
						selCity = java.net.URLDecoder.decode(selCity,"UTF-8");
						Element item = (Element) country.selectSingleNode("/country/province[@name='" +selProvince+ "']");
						Element itemArea = (Element) item.selectSingleNode("city[@name='"+selCity+"']");
						result = itemArea.attributeValue("area");
					}
					root.put("flag","ok");
					root.put("message",java.net.URLEncoder.encode(result,"UTF-8"));
				}catch(DocumentException e)
				{
					root.put("flag","error");
					e.printStackTrace();
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			root.put("flag", "error");
		}
		root.write(ServletActionContext.getResponse().getWriter());
		return null;
	}
	
	@SuppressWarnings("deprecation")
	public String department() throws IOException{
		JSONObject root = new JSONObject();
		try{
			String fileURL = ServletActionContext.getRequest().getRealPath("/WEB-INF/school.xml");
			File file = new File(fileURL);
			Document document = null;
			Element country = null;
			String result = "";
			if(file.exists())
			{
				SAXReader reader = new SAXReader();
				try{
					document = reader.read(file);
					country = document.getRootElement();
					String cname = "";
					if(ServletActionContext.getRequest().getParameter("cname")!=null&&!ServletActionContext.getRequest().getParameter("cname").toString().equals(""))
					{
						cname = java.net.URLDecoder.decode((String) ServletActionContext.getRequest().getParameter("cname"),"UTF-8");
					}else cname = "";
					String selSchool = cname.trim();
					Element item = (Element) country.selectSingleNode("/country/school[@name='" +selSchool+ "']");
					List<Element>departmentList = item.elements("department");
					Element departmentElement = null;
					for(int i = 0 ; i < departmentList.size();i++)
					{
						departmentElement = departmentList.get(i);
						result += departmentElement.attributeValue("name")+",";
					}
					result.substring(0,result.length()-1);
				}catch(DocumentException e)
				{
					e.printStackTrace();
				}
			}
			root.put("flag", "ok");
			root.put("message",java.net.URLEncoder.encode(result,"UTF-8"));
		}catch(Exception e){
			e.printStackTrace();
			root.put("flag", "error");
		}
		root.write(ServletActionContext.getResponse().getWriter());
		return null;
	}
	
	@SuppressWarnings("deprecation")
	public String sendtype() throws IOException{
		JSONObject root = new JSONObject();
		try{
			String fileURL = ServletActionContext.getRequest().getRealPath("/WEB-INF/type.xml");
			File file = new File(fileURL);
			Document document = null;
			Element country = null;
			String result = "";
			if(file.exists())
			{
				SAXReader reader = new SAXReader();
				try{
					document = reader.read(file);
					country = document.getRootElement();
					if(type.equals("generalize")){
						List<Element>generalizeList = country.elements("type");
						Element generalizeElement = null;
						for(int i = 0 ; i < generalizeList.size();i++)
						{
							generalizeElement = generalizeList.get(i);
							result += generalizeElement.attributeValue("name")+",";
						}
						result.substring(0,result.length()-1);
					}else if(type.equals("detail")){
						String mainType = ServletActionContext.getRequest().getParameter("gType");
						mainType = java.net.URLDecoder.decode(mainType,"UTF-8");
						Element item = (Element) country.selectSingleNode("/object/type[@name='" +mainType+ "']");
						List<Element>cityList = item.elements("sub-type");
						Element cityElement = null;
						for(int i = 0 ; i < cityList.size();i++)
						{
							cityElement = cityList.get(i);
							result += cityElement.attributeValue("name")+",";
						}
						result.substring(0,result.length()-1);
					}
				}catch(DocumentException e)
				{
					e.printStackTrace();
				}
			}
			root.put("flag", "ok");
			root.put("message",java.net.URLEncoder.encode(result,"UTF-8"));
		}catch(Exception e){
			e.printStackTrace();
			root.put("flag", "error");
		}
		root.write(ServletActionContext.getResponse().getWriter());
		return null;
	}

	public String schoolList() throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		String flag = "0";
		JSONObject root = new JSONObject();
		try{
			String fileURL = ServletActionContext.getRequest().getRealPath("/WEB-INF/school.xml");
			File file = new File(fileURL);
			Document document = null;
			Element country = null;
			String result = "";
			if(file.exists())
			{
				SAXReader reader = new SAXReader();
				try{
					document = reader.read(file);
					country = document.getRootElement();
					Element item = (Element) country.selectSingleNode("/country");
					List<Element>schoolList = item.elements("school");
					Element schoolElement = null;
					for(int i = 0 ; i < schoolList.size();i++)
					{
						schoolElement = schoolList.get(i);
						if(schoolElement.attributeValue("province").toString().equals(province))
							result += schoolElement.attributeValue("name")+",";
					}
					if(result.length()>0)
						result.substring(0,result.length()-1);
				}catch(DocumentException e)
				{
					e.printStackTrace();
				}
			}
			flag = "1";
			if(!result.equals("")){
				String[] sl = result.split(",");
				CommonBlockTemplate cbt = new CommonBlockTemplate(); 
				root.put("schoollist",cbt.schoolList(sl));
			}else root.put("schoollist", "");
		}catch(Exception e){
			e.printStackTrace();
		}
		root.put("flag",flag);
		root.write(response.getWriter());
		return null;
		
	}
	
	public void encode() throws UnsupportedEncodingException, IOException{
		response.getWriter().println(java.net.URLEncoder.encode(str,encodeType));
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public String getEncodeType() {
		return encodeType;
	}

	public void setEncodeType(String encodeType) {
		this.encodeType = encodeType;
	}
	
}
