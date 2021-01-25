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
		//�����û�����
		Scanner input=new Scanner(System.in);
		System.out.println("�����̵�����");
		System.out.println("Wonderland���������г����MySql������");
		System.out.println("***************************************");
		//������ʾ������Ϣ
		PetDao dao=new PetDaoImpl();
		List<Object[]> ob=dao.getAllByObject();
		System.out.println("���"+"\t"+"��������");
		for (Object[] objects : ob) {
			Pet pet=(Pet)objects[0];
			System.out.println(pet.getId()+"\t"+pet.getpName());
		}
		System.out.println("***************************************");
		System.out.println();
		System.out.println("���г������˴�MySql������");
		System.out.println("***************************************");
		//������ʾ������Ϣ
		PetOwnerDao dao2=new PetOwnerDaoImpl();
		List<Object[]> ob2=dao2.getAllByObject();
		System.out.println("���"+"\t"+"��������");
		for (Object[] objects : ob2) {
			PetOwner petowner=(PetOwner)objects[0];
			System.out.println(petowner.getId()+"\t"+petowner.getOname());
		}
		System.out.println("***************************************");
		System.out.println();
		System.out.println("���г����̵��MySql������");
		System.out.println("***************************************");
		//������ʾ�̵���Ϣ
		PetStoreDao dao3=new PetStoreDaoImpl();
		List<Object[]> ob3=dao3.getAllByObject();
		System.out.println("���"+"\t"+"�̵�����");
		for (Object[] objects : ob3) {
			PetStore petstore=(PetStore)objects[0];
			System.out.println(petstore.getId()+"\t"+petstore.getSname());
		}
		//��¼
		System.out.println("��ѡ�������¼ģʽ������1Ϊ�������˵�¼������2Ϊ�����̵��¼");
		int inputnum=input.nextInt();
		switch (inputnum) {
		case 1:
			owlogin();
			break;
		case 2:
			stlogin();
			break;
		default:
			System.err.println("�����������ݲ�������ʾ���ݣ�");
			break;
		}
	}
	//���˵�¼
	public static void owlogin(){
		Scanner input=new Scanner(System.in);
		System.out.println("���ȵ�¼�������������˵����֣�");
		String oname=input.next();
		System.out.println("�����������˵����룺");
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
		//�жϵ�¼���
		if (login>0) {
			System.out.println("-------��ϲ����½�ɹ�------123456-");
			System.out.println("-------���Ļ�����Ϣ��-------");
			//ʵ�ֲ�ѯ��ǰ������Ϣ
			ResultSet rs=odao.findByMonster(o);
			try {
				while (rs.next()) {
					o.setOname(rs.getString("Oname"));
					o.setOmoney(rs.getDouble("Omoney"));
				}
				System.out.println("���֣�"+o.getOname());
				System.out.println("Ԫ������"+o.getOmoney());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("��¼�ɹ��������Թ�����������������빺�����������1�����������������������2");
			System.out.println("1���������");
			System.out.println("2����������");
			int inputnum=input.nextInt();
			switch (inputnum) {
			case 1:
				buypet();
				
				System.out.println("-------��ѡ��Ҫ�����ĸ�һ�����������ѡ��������-------");
				//�˴�ʵ�ֹ������
				int num=input.nextInt();
				pet1.setId(num);
				account.setIdPet(num);
				account.setPyTime(date);
				int result=dao2.addAccount(account);
				/*if (result>0) {
					System.out.println("̨����ȷ����һ����Ϣ");
				}else {
					System.err.println("����");
				}*/
			result2=odao.findOwnerid(o);
			
			account.setIdPet(result2);
			pet1.setIdPetowner(result2);
				int res=pdao.updatePet(pet1);
				if (res>0) {
					System.out.println("̨����ȷ����һ����Ϣ");
				}else {
					System.err.println("����");
				}
				break;
			case 2:
				result2=odao.findOwnerid(o);
				pet1.setIdPetowner(result2);
				odao.sell(pet1);
				System.out.println("-------��ѡ��Ҫ���۵ĳ������-------");
				int num2=input.nextInt();
				System.out.println("-------��Ҫ�����ĳ�����Ϣ����-------");
				pet1.setId(num2);
				/*account.setIdPet(num2);
				account.setPyTime(date);*/
				//��ѯ��ǰѡ�еĳ�����Ϣ ���û��˶�
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
					/*System.out.println("���֣�"+pet1.getpName());
					System.out.println("���ͣ�"+pet1.getpType());*/
				System.out.println("�������ֽУ�"+pet1.getpName()+"\t"+"���������ǣ�"+pet1.getpType());
				System.out.println("��ȷ���Ƿ�������y����������n������");
				String yn=input.next();
				switch (yn) {
				case "y":
					/*System.out.println("��������");*/
					System.out.println("-------���������г����̵꣬��ѡ����Ҫ����������-------");
					//�����̵������
					PetStoreDao dao=new PetStoreDaoImpl();
					List<Object[]> ob=dao.getAllByObject();
					System.out.println("���"+"\t"+"�����̵�����");
					for (Object[] objects : ob) {
						PetStore petstore=(PetStore)objects[0];
						System.out.println(petstore.getId()+"\t"+petstore.getSname());
					}
					//̨�ʱ��¼
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
							System.out.println("̨����ȷ����һ����Ϣ");
						}else {
							System.err.println("����");
						}
					break;
				case "n":
					System.err.println("��ʾ��ȡ���۳�������ʧ�ܣ��������");
					break;
				default:
					break;
				}
				break;
			default:
				System.err.println("�����������ݲ�������ʾ���ݣ�");
				break;
			}
		}else {
			System.err.println("�����˻������ڻ��û���������󣬵�¼ʧ�ܣ�");
		}
	}
	//�̵��¼
	public static void stlogin(){
		Scanner input=new Scanner(System.in);
		System.out.println("���ȵ�¼������������̵����֣�");
		String sname=input.next();
		System.out.println("����������̵�����룺");
		String spass=input.next();
		PetStoreDao sdao=new PetStoreDaoImpl();
		int login=sdao.login(sname,spass);
		PetStore s=new PetStore();
		s.setSname(sname);
		s.setSpass(spass);
		//�жϵ�¼���
		if (login>0) {
			System.out.println("-------��ϲ�ɹ���¼-------");
			System.out.println("-------�����̵�Ļ�����Ϣ��-------");
			//ʵ�ֲ�ѯ��ǰ������Ϣ
			ResultSet rs=sdao.findByMonster(s);
			try {
				while (rs.next()) {
					s.setSname(rs.getString("Sname"));
					s.setSmoney(rs.getDouble("Smoney"));
				}
				System.out.println("���֣�"+s.getSname());
				System.out.println("Ԫ������"+s.getSmoney());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("��¼�ɹ������Խ������²���");
			System.out.println("1:�������");
			System.out.println("2:��������");
			System.out.println("3:��������");
			System.out.println("4:��ѯ���۳���");
			System.out.println("5:�鿴�̵�������");
			System.out.println("6:�鿴�̵���Ŀ");
			System.out.println("7:�������̵�");
			System.out.println("�������Ҫִ�в�����ѡ��������룬�˳�������0");
		}else {
			System.err.println("�����˻������ڻ��û���������󣬵�¼ʧ�ܣ�");
		}
	}
	//����
	public static void buypet(){
		PetDao dao=new PetDaoImpl();
	
		Scanner input=new Scanner(System.in);
		System.out.println("-------������ѡ��Ҫ����Χ��ֻ����ѡ��������-------");
		System.out.println("1�����������");
		System.out.println("2����������������");
		int inputnum=input.nextInt();
		switch (inputnum) {
		case 1:
			//������
			System.out.println("-------�����ǿ�����-------");
			List<Pet> all=dao.getAll();
			System.out.println("���"+"\t"+"��������"+"\t"+"����"+"\t"+"Ԫ����");
			//��������
			for (Pet pet : all) {
				System.out.println(pet.getId()+"\t"+pet.getpName()+"\t"+pet.getpType()+"\t"+pet.getpMoney());
			}
			
			break;
		case 2:
			//����������
			System.out.println("-------����������������-------");
			List<Pet> all2=dao.getAll2();
			System.out.println("���"+"\t"+"��������"+"\t"+"����"+"\t"+"Ԫ����");
			//��������
			for (Pet pet : all2) {
				System.out.println(pet.getId()+"\t"+pet.getpName()+"\t"+pet.getpType()+"\t"+pet.getpMoney());
			}
			
			break;
		default:
			break;
		}
		
	}
	//����
	/*public static void breed(){
		Scanner input=new Scanner(System.in);
		System.out.println("-------�ҵĳ����б�-------");
		
	}*/
}
