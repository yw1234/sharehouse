package services.share;

import java.util.List;
import java.util.Map;

import model.Goods;
import model.IdleOrder;
import model.Picture;

import dao.base.BaseDao;

public interface ShareService {
	
	public BaseDao getBaseDao();
	
	/**
	 * 发布闲置
	 * @param goods
	 * @return
	 */
	public boolean sendIdle(Goods goods);
	
	/**
	 * 添加闲置照片
	 * @param id
	 * @param picList
	 * @return boolean
	 */
	public boolean addIdlePicture(Long id,String []picList);
	
	/**
	 * 获取闲置图片
	 * @param id
	 * @param size
	 * @param pageno
	 * @return
	 */
	public List<Picture> getIdlePictureList(Long id ,int size,int pageno);
	
	/**
	 * 获取闲置列表
	 * @param userid
	 * @param size
	 * @param pageno
	 * @param params
	 * @return
	 */
	public List getIdleList(Long userid,int size,int pageno,Map params);
	
	/**
	 * 物品是否存在
	 * @param userid
	 * @param goodsid
	 * @return
	 */
	public boolean isIdleExists(Long userid,Long goodsid);
	
	/**
	 * 删除闲置
	 * @param userid
	 * @param goodsid
	 * @param path
	 * @return
	 */
	public boolean deleteIdle(Long userid,Long goodsid,String path);
	
	/**
	 * 获取闲置发布人
	 * @param idleId
	 * @return
	 */
	public Long getIdleSender(Long idleId);
	
	/**
	 * 获取全部闲置信息
	 * @param id
	 * @return
	 */
	public Map getGoodsInfo(Long id);
	
	/**
	 * 按字段获取闲置信息
	 * @param id
	 * @return
	 */
	public Map getGoodsInfo(Long id,String fields);
	
	/**
	 * 获取闲置详情
	 * @param id
	 * @return
	 */
	public Map getGoodsDetails(Long id);
	
	/**
	 * 更新物品信息
	 * @param goods
	 * @return
	 */
	public boolean updateGoods(Goods goods);
	
	/**
	 * 添加订单
	 * @param order
	 * @return
	 */
	public boolean addOrder(IdleOrder order);
	
	/**
	 * 删除订单
	 * @param id
	 * @return
	 */
	public boolean deleteOrder(Long id);
	
	/**
	 * 获取当前用户的订单列表
	 * @param userid
	 * @return
	 */
	public List getMyOrderInfo(Long userid);
	
	/**
	 * 通过id获取订单
	 * @param orderid
	 * @return
	 */
	public Map getOrderInfoById(Long orderid);
	/**
	 * 通过闲置id获取订单
	 * @param idleid
	 * @param userid
	 * @return
	 */
	public Map getOrderInfoByIdle(Long idleid , Long userid);
	
	/**
	 * 确认订单
	 * @param idleid
	 * @param userid
	 * @return
	 */
	public boolean confirmOrder(Long idleid,Long userid);
	
}
