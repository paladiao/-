package process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class FileSystem {
    void exe(){
        User u1 = new User("Julius","185284");
        Dictionary d1 = new Dictionary("Julius's dictionary");
        User u2 = new User("Caseer","185204");
        Dictionary d2 = new Dictionary("Caseer's dictionary");
        Dictionary r_dictionary = new Dictionary("root",true);
        MFD mfd = new MFD();
        AFD afd = new AFD();//运行文件目录
        mfd.addUser(u1,d1); mfd.addUser(u2,d2);
        Scanner scanner = new Scanner(System.in);
        String input = "";
        int chance =0;
        while(true){
            if(input.equals("exit")) return;
            System.out.print("请输入登录账号：");
            input = scanner.nextLine();
            if(mfd.contain(input)){
                User worker = mfd.get_user(input);//登入用户
                Dictionary temp =  mfd.get_dictionary(worker);//用户目录
                if(worker == null){
                    System.out.println("25");
                }else{
                    System.out.print(worker.name+":\\>");
                    input = scanner.nextLine();
                    while(!input.equals("end")){
                        String[] strs = {" "," "};
                        strs = input.split(" ");
                        //System.out.println(input);
                        if(input.equals("dir")){
                            for(Dictionary_Item x:temp.list){
                                System.out.println(x.file_name+"  "+x.points+"  "+ Arrays.toString(x.protection_code)+"  "+x.file_size);
                            }
//                            System.out.println("afd");
//                            for(AFD_item x:afd.process_files_list.keySet()){
//                                System.out.println(x.file_name+"  "+x.points_file+"  "+"  ");
//                            }
                        }
                        if(strs[0].equals("open")){
                            if(!temp.d_contains(strs[1])){
                              System.out.println("无此文件");
                            }
                            else {
                                Dictionary_Item dictionary_item = temp.d_getfile(strs[1]);
                                AFD_item open_file = new AFD_item (dictionary_item.file_name,dictionary_item.protection_code[2]);
                                open_file.setPoints_file(dictionary_item.points);
                                afd.addfile(open_file);
                                System.out.println("打开文件成功");
                            }
                    }
                        if(strs[0].equals("close")){
                            if(afd.remove(strs[1])){
                                temp.d_getfile(strs[1]).points = temp.d_getfile(strs[1]).hashCode();
                                System.out.println(temp.d_getfile(strs[1]).points);
                                System.out.println("关闭成功");
                            }else {
                                System.out.println("关闭失败");
                            }
                        }
                        if(strs[0].equals("create")){
                            if(chance == 10) {System.out.println("一次只能创建10个文件");}
                            if(!(chance == 10)) {
                                Dictionary_Item dictionary_item = new Dictionary_Item(strs[1]);
                                //先添加创建INODE,后进行文件内容的创建。
                                dictionary_item.points = dictionary_item.hashCode();
                                temp.d_addfile(dictionary_item);
                                chance++;
                                System.out.println("创建文件成功");}
                        }
                        if(strs[0].equals("delete")) {
                            if (temp.remove(strs[1])) {
                                afd.remove(strs[1]);
                                System.out.println("删除成功");
                            } else{
                                System.out.println("删除失败");
                            }
                        }

                        if(strs[0].equals("read")){
                            AFD_item afd_item = afd.a_getfile(strs[1]);
                            Dictionary_Item dictionary_item = temp.d_getfile(strs[1]);
                            if(dictionary_item == null || dictionary_item.protection_code[0]==false){
                                System.out.println("无此文件或此文件不可读");
                            }else {
                                if(afd_item ==null){
                                    afd_item = new AFD_item (dictionary_item.file_name,dictionary_item.protection_code[2]);
                                    afd_item.setPoints_file(dictionary_item.points);
                                    if(afd_item.points_file-dictionary_item.points<dictionary_item.file_size){
                                        System.out.println("读前指针："+afd_item.points_file);
                                        afd.addfile(afd_item);
                                        afd_item.points_file ++;
                                        System.out.println("读后指针："+afd_item.points_file);
                                    }else {
                                        System.out.println("已至文件结尾");
                                    }

                                }else {
                                    if(afd_item.points_file-dictionary_item.points<dictionary_item.file_size) {
                                        System.out.println("读前指针：" + afd_item.points_file);
                                        afd.addfile(afd_item);
                                        afd_item.points_file++;
                                        System.out.println("读后指针：" + afd_item.points_file);
                                    }else{
                                        System.out.println("已至文件结尾");
                                    }
                                }
                            }
                        }
                        if(strs[0].equals("write")){
                            AFD_item afd_item = afd.a_getfile(strs[1]);
                            Dictionary_Item dictionary_item = temp.d_getfile(strs[1]);
                            if(dictionary_item == null || dictionary_item.protection_code[1]==false){
                                System.out.println("无此文件或此文件不可写");
                            }else {
                                if(afd_item ==null){
                                    afd_item = new AFD_item (dictionary_item.file_name,dictionary_item.protection_code[2]);
                                    afd_item.setPoints_file(dictionary_item.points);
                                    System.out.println("写前指针："+afd_item.points_file);
                                    afd.addfile(afd_item);
                                    afd_item.points_file ++;
                                    temp.d_getfile(strs[1]).file_size++;
                                    System.out.println("写后指针："+afd_item.points_file);
                                }else {
                                    System.out.println("写前指针："+afd_item.points_file);
                                    afd.addfile(afd_item);
                                    afd_item.points_file ++;
                                    temp.d_getfile(strs[1]).file_size++;
                                    System.out.println("写后指针："+afd_item.points_file);
                                }
                            }
                        }
                        System.out.print(worker.name+":\\>");
                        input = scanner.nextLine();
                    }
                }
            }
            else {
                System.out.println("无此用户");
            }
        }
    }

    class User{
        private String name;
        private String ID;

        User(String name,String ID){
            this.name = name;
            this.ID = ID;
        }
    }

    class File {
        protected String file_name;
        protected int physical_address;
        protected int length;
        protected ArrayList messages ;
        protected boolean[] protection_code ;//三位保护码，初始化为0
        private int points;
        File(){}
        File(String file_name) {
            this.file_name = file_name;
            points = 0;
        }

        @Override
        public String toString() {
            return file_name;
        }
    }
    class Dictionary_Item{
        String file_name;
        private boolean []protection_code;
        private int file_size;
        private int points;
        Dictionary_Item(String file_name){
            this.file_name = file_name;
            protection_code = new boolean[]{true, true, true};
            file_size = 0;
        }
    }
    class Dictionary extends File{
        private ArrayList<Dictionary_Item> list;
        private boolean root;

        Dictionary(String file_name){
            super(file_name);
            list = new ArrayList<>();
        }

        Dictionary(String file_name,boolean root){
            this(file_name);
            this.root = root;
        }

        void d_addfile(Dictionary_Item dictionary_item){
            list.add(dictionary_item);
        }
        Dictionary_Item d_getfile(String file_name){
            for(int i = 0;i <list.size();i++){
                if(list.get(i).file_name.equals(file_name)){
                    return list.get(i);
                }
            }
            return null;
        }

        boolean d_contains(String file_name){
            for(Dictionary_Item X:list){
                if(X.file_name.equals(file_name)) return true;
            }
            return  false;
        }

        boolean remove(String file){
            if(d_contains(file)) {
                list.remove(d_getfile(file));
                return true;
            }
            return false;
        }

    }

    class MFD{
        private HashMap<User,Dictionary> dictionary_list;
        MFD(){
            dictionary_list = new HashMap<>(10);
        }
        void addUser(User user,Dictionary dictionary){
            dictionary_list.put(user, dictionary);
        }
        boolean contain(String user_name){
            for(User x:dictionary_list.keySet()){
                if(x.name.equals(user_name)) return true;
            }
            return false;
        }
        User get_user(String user_name){
            for(User x:dictionary_list.keySet()){
                if(x.name.equals(user_name)) return x;
            }
            return null;
        }
        Dictionary get_dictionary(User user){
            return dictionary_list.get(user);
        }
    }


    class AFD{
        private HashMap<AFD_item,Integer> process_files_list;
        AFD(){
            process_files_list = new HashMap<>(10);
        }
//        boolean a_contain(AFD_item file){
//            if(process_files_list.containsKey(file)) return  true;
//            return false;
//        }
//        boolean a_contain(String file){
//            for(AFD_item X:process_files_list.keySet()){
//                if (X.file_name.equals(file)) {
//                    return true;
//                }
//            }return  false;
//        }
        AFD_item a_getfile(String file){
            for(AFD_item X:process_files_list.keySet()){
                if (X.file_name.equals(file)) {
                    return X;
                }
            }return  null;
        }
        void addfile(AFD_item file){
            if(process_files_list.containsKey(file)){
                Integer integer = process_files_list.get(file)+1;
                process_files_list.put(file,integer);
            }else {
                process_files_list.put(file,Integer.valueOf(1));
            }
        }
        boolean remove(String file){
            AFD_item afd_item = null;
            for(AFD_item X:process_files_list.keySet()){
               if(X.file_name.equals(file)) afd_item = X;
            }
            process_files_list.remove(afd_item);
            return afd_item !=null;
        }
    }
    class AFD_item extends Dictionary_Item{
        private boolean protection;
        private int points_file;
        AFD_item(String file_name,boolean protection){
            super(file_name);
            this.protection = protection;
        }
        public void setPoints_file(int points_file) {
            this.points_file = points_file;
        }
    }
}