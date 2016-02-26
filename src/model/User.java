package model;

import java.util.Date;

public class User extends Customer implements java.io.Serializable{
		
		public User(){}					//构造函数
		String nickname;     			//拼音
		String nickhead;					//拼音头
		String show_email;				//常用邮箱
		String sex;						//性别
		Integer age;						//年龄
		String birthday;					//生日
		String school;					//所在学校
		String department;				//所在院系
		String hs_year;					//入学年份
		String educational;				//学历
		String campus;					//校区
		String constellation;			//星座
		String sx;						//生肖
		String marry;					//婚恋
		String blood;					//血型
		String home;						//家乡
		String introduce;				//自我介绍
		String autologin = "0";			//是否设置自动登录 0 否 ,1 是
		String phone;					//电话号码
		String province;				//所在地_省
		String city;					//所在地_市
		String qq;						//qq号码		
		String open_id;					//个人网站url(暂时无用,先加上)
		String type = "0";				//用户类型---0 普通用户 
		String head_ico;				//用户头像所在位置的字符串
		String head_ico_big;			//用户头像(小图)所在位置的字符串
		Integer visted_times = 0;		//被访问次数
		Integer coin = 0;					//积分
		Integer reputation = 0;				//信誉度
		Integer level = 1;					//等级
		Integer friend_number = 0;			//好友数量
		Integer circle_number = 0;			//圈子数量
		Integer continue_online = 1;		//连续在线天数
		Integer send_active = 0;			//发布活跃度
		Integer login_active = 0;			//登录或阅读
		Date lastsendtime;					//上次发布时间
		String registerByAPI = "0";			//是否为其他方式注册
		String dorm;						//宿舍或住址,送货用
		/**
		 * 以上为基本信息
		 * 若有不足可继续添加
		 * 隐私设置选项
		 */
		String privacy_myPage = "0";
		String privacy_myCircleList = "0";
		String privacy_myUserList = "0";
		String privacy_myMsgList = "0";
		String privacy_useNickName = "0";
		String privacy_pInfo = "0";
		String privacy_pShowUser = "0";
		String privacy_pShowCircle = "0";
		
		
		public String getNickhead() {
			return nickhead;
		}
		public void setNickhead(String nickhead) {
			this.nickhead = nickhead;
		}
		public String getNickname() {
			return nickname;
		}
		public void setNickname(String nickname) {
			this.nickname = nickname;
		}
		public String getSex() {
			return sex;
		}
		public void setSex(String sex) {
			this.sex = sex;
		}
		public Integer getAge() {
			return age;
		}
		public void setAge(Integer age) {
			this.age = age;
		}
		public String getBirthday() {
			return birthday;
		}
		public void setBirthday(String birthday) {
			this.birthday = birthday;
		}
		public String getAutologin() {
			return autologin;
		}
		public void setAutologin(String autologin) {
			this.autologin = autologin;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getProvince() {
			return province;
		}
		public void setProvince(String province) {
			this.province = province;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getQq() {
			return qq;
		}
		public void setQq(String qq) {
			this.qq = qq;
		}
		public String getOpen_id() {
			return open_id;
		}
		public void setOpen_id(String openId) {
			open_id = openId;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getHead_ico() {
			return head_ico;
		}
		public void setHead_ico(String headIco) {
			head_ico = headIco;
		}
		public Integer getVisted_times() {
			return visted_times;
		}
		public void setVisted_times(Integer vistedTimes) {
			visted_times = vistedTimes;
		}
		public Integer getCoin() {
			return coin;
		}
		public void setCoin(Integer coin) {
			this.coin = coin;
		}
		public Integer getReputation() {
			return reputation;
		}
		public void setReputation(Integer reputation) {
			this.reputation = reputation;
		}
		public Integer getLevel() {
			return level;
		}
		public void setLevel(Integer level) {
			this.level = level;
		}
		public Integer getFriend_number() {
			return friend_number;
		}
		public void setFriend_number(Integer friendNumber) {
			friend_number = friendNumber;
		}
		public Integer getCircle_number() {
			return circle_number;
		}
		public void setCircle_number(Integer circleNumber) {
			circle_number = circleNumber;
		}
		public Integer getContinue_online() {
			return continue_online;
		}
		public void setContinue_online(Integer continueOnline) {
			continue_online = continueOnline;
		}
		public String getSchool() {
			return school;
		}
		public void setSchool(String school) {
			this.school = school;
		}
		public String getConstellation() {
			return constellation;
		}
		public void setConstellation(String constellation) {
			this.constellation = constellation;
		}
		
		public String getSx() {
			return sx;
		}
		public void setSx(String sx) {
			this.sx = sx;
		}
		public String getMarry() {
			return marry;
		}
		public void setMarry(String marry) {
			this.marry = marry;
		}
		public String getBlood() {
			return blood;
		}
		public void setBlood(String blood) {
			this.blood = blood;
		}
		public String getHome() {
			return home;
		}
		public void setHome(String home) {
			this.home = home;
		}
		public String getIntroduce() {
			return introduce;
		}
		public void setIntroduce(String introduce) {
			this.introduce = introduce;
		}
		public Integer getSend_active() {
			return send_active;
		}
		public void setSend_active(Integer sendActive) {
			send_active = sendActive;
		}
		public Integer getLogin_active() {
			return login_active;
		}
		public void setLogin_active(Integer loginActive) {
			login_active = loginActive;
		}
		public String getPrivacy_myPage() {
			return privacy_myPage;
		}
		public void setPrivacy_myPage(String privacyMyPage) {
			privacy_myPage = privacyMyPage;
		}
		public String getPrivacy_myCircleList() {
			return privacy_myCircleList;
		}
		public void setPrivacy_myCircleList(String privacyMyCircleList) {
			privacy_myCircleList = privacyMyCircleList;
		}
		public String getPrivacy_myUserList() {
			return privacy_myUserList;
		}
		public void setPrivacy_myUserList(String privacyMyUserList) {
			privacy_myUserList = privacyMyUserList;
		}
		public String getPrivacy_useNickName() {
			return privacy_useNickName;
		}
		public void setPrivacy_useNickName(String privacyUseNickName) {
			privacy_useNickName = privacyUseNickName;
		}
		public String getPrivacy_pInfo() {
			return privacy_pInfo;
		}
		public void setPrivacy_pInfo(String privacyPInfo) {
			privacy_pInfo = privacyPInfo;
		}
		public String getPrivacy_pShowUser() {
			return privacy_pShowUser;
		}
		public void setPrivacy_pShowUser(String privacyPShowUser) {
			privacy_pShowUser = privacyPShowUser;
		}
		public String getPrivacy_pShowCircle() {
			return privacy_pShowCircle;
		}
		public void setPrivacy_pShowCircle(String privacyPShowCircle) {
			privacy_pShowCircle = privacyPShowCircle;
		}
		public String getShow_email() {
			return show_email;
		}
		public void setShow_email(String showEmail) {
			show_email = showEmail;
		}
		public String getPrivacy_myMsgList() {
			return privacy_myMsgList;
		}
		public void setPrivacy_myMsgList(String privacyMyMsgList) {
			privacy_myMsgList = privacyMyMsgList;
		}
		public String getHead_ico_big() {
			return head_ico_big;
		}
		public void setHead_ico_big(String headIcoBig) {
			head_ico_big = headIcoBig;
		}
		public Date getLastsendtime() {
			return lastsendtime;
		}
		public void setLastsendtime(Date lastsendtime) {
			this.lastsendtime = lastsendtime;
		}
		public String getDepartment() {
			return department;
		}
		public void setDepartment(String department) {
			this.department = department;
		}
		public String getRegisterByAPI() {
			return registerByAPI;
		}
		public void setRegisterByAPI(String registerByAPI) {
			this.registerByAPI = registerByAPI;
		}
		public String getHs_year() {
			return hs_year;
		}
		public void setHs_year(String hs_year) {
			this.hs_year = hs_year;
		}
		public String getEducational() {
			return educational;
		}
		public void setEducational(String educational) {
			this.educational = educational;
		}
		public String getDorm() {
			return dorm;
		}
		public void setDorm(String dorm) {
			this.dorm = dorm;
		}
		public String getCampus() {
			return campus;
		}
		public void setCampus(String campus) {
			this.campus = campus;
		}
		
}
