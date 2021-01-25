package cn.bdqn.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import cn.bdqn.dao.AccountDao;
import cn.bdqn.dao.PetDao;
import cn.bdqn.dao.PetOwnerDao;
import cn.bdqn.dao.PetStoreDao;
import cn.bdqn.dao.impl.AccountDaoImpl;
import cn.bdqn.dao.impl.PetDaoImpl;
import cn.bdqn.dao.impl.PetOwnerDaoImpl;
import cn.bdqn.dao.impl.PetStoreDaoImpl;
import cn.bdqn.pojo.Account;
import cn.bdqn.pojo.Pet;
import cn.bdqn.pojo.PetOwner;
import cn.bdqn.pojo.PetStore;

public class TestMain {

	public static void main(String[] args) {
		//接受用户输入
		Scanner input=new Scanner(System.in);
		System.out.println("宠物商店启动");
		System.out.println("Wonderland醒来，所有宠物从MySql中醒来");
		System.out.println("***************************************");
		//遍历显示宠物信息
		PetDao dao=new PetDaoImpl();
		List<Object[]> ob=dao.getAllByObject();
		System.out.println("序号"+"\t"+"宠物名称");
		for (Object[] objects : ob) {
			Pet pet=(Pet)objects[0];
			System.out.println(pet.getId()+"\t"+pet.getpName());
		}
		System.out.println("***************************************");
		System.out.println();
		System.out.println("所有宠物主人从MySql中醒来");
		System.out.println("***************************************");
		//遍历显示主人信息
		PetOwnerDao dao2=new PetOwnerDaoImpl();
		List<Object[]> ob2=dao2.getAllByObject();
		System.out.println("序号"+"\t"+"主人名称");
		for (Object[] objects : ob2) {
			PetOwner petowner=(PetOwner)objects[0];
			System.out.println(petowner.getId()+"\t"+petowner.getOname());
		}
		System.out.println("***************************************");
		System.out.println();
		System.out.println("所有宠物商店从MySql中醒来");
		System.out.println("***************************************");
		//遍历显示商店信息
		PetStoreDao dao3=new PetStoreDaoImpl();
		List<Object[]> ob3=dao3.getAllByObject();
		System.out.println("序号"+"\t"+"商店名称");
		for (Object[] objects : ob3) {
			PetStore petstore=(PetStore)objects[0];
			System.out.println(petstore.getId()+"\t"+petstore.getSname());
		}
		//登录
		System.out.println("请选择输入登录模式，输入1为宠物主人登录，输入2为宠物商店登录");
		int inputnum=input.nextInt();
		switch (inputnum) {
		case 1:
			owlogin();
			break;
		case 2:
			stlogin();
			break;
		default:
			System.err.println("错误：输入内容不符合提示内容！");
			break;
		}
	}
	//主人登录
	public static void owlogin(){
		Scanner input=new Scanner(System.in);
		System.out.println("请先登录，请您输入主人的名字：");
		String oname=input.next();
		System.out.println("请您输入主人的密码：");
		String opass=input.next();
		PetOwnerDao odao=new PetOwnerDaoImpl();
		int login=odao.login(oname, opass);
		PetOwner o=new PetOwner();
		o.setOname(oname);
		o.setOpass(opass);
		PetStore s=new PetStore();
		Pet pet1=new Pet();
		PetDao pdao=new PetDaoImpl();
		Account account=new Account();
		AccountDao dao2=new AccountDaoImpl();
		Date date=new Date();
		PetStoreDao sdao=new PetStoreDaoImpl();
		int result2=0;
		int result4=0;
		//判断登录结果
		if (login>0) {
			System.out.println("-------恭喜您登陆成功------123456-");
			System.out.println("-------您的基本信息：-------");
			//实现查询当前主人信息
			ResultSet rs=odao.findByMonster(o);
			try {
				while (rs.next()) {
					o.setOname(rs.getString("Oname"));
					o.setOmoney(rs.getDouble("Omoney"));
				}
				System.out.println("名字："+o.getOname());
				System.out.println("元宝数："+o.getOmoney());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("登录成功，您可以购买和卖出宠物，如果您想购买宠物请输入1，如果您想卖出宠物请输入2");
			System.out.println("1：购买宠物");
			System.out.println("2：卖出宠物");
			int inputnum=input.nextInt();
			switch (inputnum) {
			case 1:
				buypet();
				
				System.out.println("-------请选择要购买哪个一个宠物，并输入选择项的序号-------");
				//此处实现购买宠物
				int num=input.nextInt();
				pet1.setId(num);
				account.setIdPet(num);
				account.setPyTime(date);
				int result=dao2.addAccount(account);
				/*if (result>0) {
					System.out.println("台帐正确插入一条信息");
				}else {
					System.err.println("错误：");
				}*/
			result2=odao.findOwnerid(o);
			
			account.setIdPet(result2);
			pet1.setIdPetowner(result2);
				int res=pdao.updatePet(pet1);
				if (res>0) {
					System.out.println("台帐正确插入一条信息");
				}else {
					System.err.println("错误：");
				}
				break;
			case 2:
				result2=odao.findOwnerid(o);
				pet1.setIdPetowner(result2);
				odao.sell(pet1);
				System.out.println("-------请选择要出售的宠物序号-------");
				int num2=input.nextInt();
				System.out.println("-------您要卖出的宠物信息如下-------");
				pet1.setId(num2);
				/*account.setIdPet(num2);
				account.setPyTime(date);*/
				//查询当前选中的宠物信息 给用户核对
				ResultSet rs2=pdao.getById(pet1);
				try {
					while (rs2.next()) {
						pet1.setpName(rs2.getString("pName"));
						pet1.setpType(rs2.getString("pType"));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					/*System.out.println("名字："+pet1.getpName());
					System.out.println("类型："+pet1.getpType());*/
				System.out.println("宠物名字叫："+pet1.getpName()+"\t"+"宠物类型是："+pet1.getpType());
				System.out.println("请确认是否卖出，y代表卖出，n代表不卖");
				String yn=input.next();
				switch (yn) {
				case "y":
					/*System.out.println("方法正常");*/
					System.out.println("-------下面是现有宠物商店，请选择您要卖给买家序号-------");
					//遍历商店表数据
					PetStoreDao dao=new PetStoreDaoImpl();
					List<Object[]> ob=dao.getAllByObject();
					System.out.println("序号"+"\t"+"宠物商店名称");
					for (Object[] objects : ob) {
						PetStore petstore=(PetStore)objects[0];
						System.out.println(petstore.getId()+"\t"+petstore.getSname());
					}
					//台帐表记录
					int storenum=input.nextInt();
					s.setId(storenum);
					account.setIdPet(storenum);
					account.setPyTime(date);
					int result3=dao2.addAccount(account);
					result4=sdao.findStoreid(s);
					
					account.setIdPet(result4);
					pet1.setIdPetstore(result4);
						int res2=pdao.updatePet2(pet1);
						if (res2>0) {
							System.out.println("台帐正确插入一条信息");
						}else {
							System.err.println("错误：");
						}
					break;
				case "n":
					System.err.println("提示：取消售出，交易失败，程序结束");
					break;
				default:
					break;
				}
				break;
			default:
				System.err.println("错误：输入内容不符合提示内容！");
				break;
			}
		}else {
			System.err.println("错误：账户不存在或用户名密码错误，登录失败！");
		}
	}
	//商店登录
	public static void stlogin(){
		Scanner input=new Scanner(System.in);
		System.out.println("请先登录，请输入宠物商店名字：");
		String sname=input.next();
		System.out.println("请输入宠物商店的密码：");
		String spass=input.next();
		PetStoreDao sdao=new PetStoreDaoImpl();
		int login=sdao.login(sname,spass);
		PetStore s=new PetStore();
		s.setSname(sname);
		s.setSpass(spass);
		//判断登录结果
		if (login>0) {
			System.out.println("-------恭喜成功登录-------");
			System.out.println("-------宠物商店的基本信息：-------");
			//实现查询当前主人信息
			ResultSet rs=sdao.findByMonster(s);
			try {
				while (rs.next()) {
					s.setSname(rs.getString("Sname"));
					s.setSmoney(rs.getDouble("Smoney"));
				}
				System.out.println("名字："+s.getSname());
				System.out.println("元宝数："+s.getSmoney());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("登录成功，可以进行如下操作");
			System.out.println("1:购买宠物");
			System.out.println("2:卖出宠物");
			System.out.println("3:培育宠物");
			System.out.println("4:查询待售宠物");
			System.out.println("5:查看商店结余宠物");
			System.out.println("6:查看商店账目");
			System.out.println("7:开宠物商店");
			System.out.println("请根据需要执行操作，选择序号输入，退出请输入0");
		}else {
			System.err.println("错误：账户不存在或用户名密码错误，登录失败！");
		}
	}
	//购买
	public static void buypet(){
		PetDao dao=new PetDaoImpl();
	
		Scanner input=new Scanner(System.in);
		System.out.println("-------请输入选择要购买范围：只输入选择项的序号-------");
		System.out.println("1：购买库存宠物");
		System.out.println("2：购买新培育宠物");
		int inputnum=input.nextInt();
		switch (inputnum) {
		case 1:
			//库存宠物
			System.out.println("-------以下是库存宠物-------");
			List<Pet> all=dao.getAll();
			System.out.println("序号"+"\t"+"宠物名称"+"\t"+"类型"+"\t"+"元宝数");
			//遍历数据
			for (Pet pet : all) {
				System.out.println(pet.getId()+"\t"+pet.getpName()+"\t"+pet.getpType()+"\t"+pet.getpMoney());
			}
			
			break;
		case 2:
			//新培育宠物
			System.out.println("-------以下是新培育宠物-------");
			List<Pet> all2=dao.getAll2();
			System.out.println("序号"+"\t"+"宠物名称"+"\t"+"类型"+"\t"+"元宝数");
			//遍历数据
			for (Pet pet : all2) {
				System.out.println(pet.getId()+"\t"+pet.getpName()+"\t"+pet.getpType()+"\t"+pet.getpMoney());
			}
			
			break;
		default:
			break;
		}
		
	}
	//出售
	/*public static void breed(){
		Scanner input=new Scanner(System.in);
		System.out.println("-------我的宠物列表-------");
		
	}*/
}
