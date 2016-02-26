package services.common;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.OptionsKey;

import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import dao.base.BaseDao;
public class CommonServiceImpl implements CommonService{

	BaseDao baseDao;
	
	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public boolean isSchoolExists(String cName) {
		boolean flag = false;
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
						if(schoolElement.attributeValue("name").toString().equals(cName))
						{
							flag = true;
							break;
						}
					}
				}catch(DocumentException e)
				{
					e.printStackTrace();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public String[] getObjType1() {
		String []t1 = null;
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
					Element item = (Element) country.selectSingleNode("/object");
					List<Element>schoolList = item.elements("type");
					Element schoolElement = null;
					for(int i = 0 ; i < schoolList.size();i++)
					{
						schoolElement = schoolList.get(i);
						result+=schoolElement.attributeValue("name").toString()+",";
					}
					t1 = result.substring(0,result.length()-1).split(",");
				}catch(DocumentException e)
				{
					e.printStackTrace();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return t1;
	}

	public String[] getObjType2(String type1) {
		// TODO Auto-generated method stub
		String []t2 = null;
		try{
			String fileURL = ServletActionContext.getRequest().getRealPath("/WEB-INF/type.xml");
			File file = new File(fileURL);
			Document document = null;
			Element country = null;
			String result = "";
			if(type1!=null&&!type1.equals("")&&file.exists())
			{
				SAXReader reader = new SAXReader();
				try{
					document = reader.read(file);
					country = document.getRootElement();
					Element item = (Element) country.selectSingleNode("/object/type[@name='" +type1+ "']");
					List<Element>schoolList = item.elements("sub-type");
					Element schoolElement = null;
					for(int i = 0 ; i < schoolList.size();i++)
					{
						schoolElement = schoolList.get(i);
						result+=schoolElement.attributeValue("name").toString()+",";
					}
					t2 = result.substring(0,result.length()-1).split(",");
				}catch(DocumentException e)
				{
					e.printStackTrace();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return t2;
	}

	public boolean addOptKey(OptionsKey rp) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try{
			flag = baseDao.saveOrUpdate(rp);
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	
	public boolean deleteOptKey(String key) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try{
			flag = baseDao.delete(OptionsKey.class, key);
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public boolean deleteOptKey(Long uid, String opt) {
		boolean flag = false;
		try{
			String sql = "delete from options_key where uid = "+uid+" and opt = '"+opt+"'";
			flag = baseDao.execute(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	
	public Map getResetUid(String key) {
		// TODO Auto-generated method stub
		Map map = null;
		try{
			String hql = "select uid,add_time from OptionsKey where key = '"+key+"' and option = 'reset'";
			List list = baseDao.getPageList(hql, 0, 1);
			if(list !=null && list.size()>0){
				Object []obj = (Object[]) list.get(0);
				map = new HashMap();
				map.put("uid",obj[0]);
				map.put("add_time",obj[1]);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}

	public Map getEmailValidationUid(String key) {
		Map map = null;
		try{
			String hql = "select uid,add_time from OptionsKey where key = '"+key+"' and option = 'emailValidation'";
			List list = baseDao.getPageList(hql, 0, 1);
			if(list !=null && list.size()>0){
				Object []obj = (Object[]) list.get(0);
				map = new HashMap();
				map.put("uid",obj[0]);
				map.put("add_time",obj[1]);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}

	public String[] getCampus(String sName) {
		String [] campus = null;
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
					List<Element>departmentList = item.elements("campus");
					Element departmentElement = null;
					for(int i = 0 ; i < departmentList.size();i++)
					{
						departmentElement = departmentList.get(i);
						result += departmentElement.attributeValue("name")+",";
					}
					campus = result.substring(0,result.length()-1).split(",");
				}catch(DocumentException e)
				{
					e.printStackTrace();
				}
			
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return campus;
	}

	
}
