package process;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.ArrayList;

public class AddressingMode {

    //页表格式：页号__标志__主存块号__磁盘位置
    public void exe() {
        pageTable p = initialPageTable();
        ArrayList<instructions> instructionsArrayList = initialInstructions();
        FIFO fifo = initailFIFO();
        for (instructions X : instructionsArrayList) {
            if (p.getItem(X.pages).sign != 0) {
                int address = p.getItem(X.pages).blocks * 128 + X.offset;
                System.out.println("操作：" + X.code + "  address: " + address);
                if(!(X.code.equals("存")||X.code.equals("取"))||p.getItem(X.pages).dirty_bit==0){
                    p.getItem(X.pages).setDirty_bit(1);
                    fifo.getItems(X.pages).setDirty_bit(1);
                }
            }
            if (p.getItem(X.pages).sign == 0){
                System.out.println("*该页页号");
                if (!fifo.isFull()) {
                    fifo.addFrame(p.getItem(X.pages));
                    System.out.println("????");
                }else {
                    list_item temp = fifo.pop();
                    temp.setSign(0);
                    list_item cur = p.getItem(X.pages);
                    cur.setSign(1);
                    if(temp.getDirty_bit() != 1){
                        cur.setBlocks(temp.blocks);
                        temp.setBlocks(-1);
                        fifo.addFrame(cur);
                        System.out.println("INPUT :"+cur.pages);
                        int address = p.getItem(X.pages).blocks * 128 + X.offset;
                        System.out.println("操作：" + X.code + "  address: " + address);
                    }else{
                        cur.setBlocks(temp.blocks);
                        temp.setBlocks(-1);
                        fifo.addFrame(cur);
                        System.out.println("OUT："+temp.pages);
                        System.out.println("INPUT :"+cur.pages);
                        int address = p.getItem(X.pages).blocks * 128 + X.offset;
                        System.out.println("操作：" + X.code + "  address: " + address);
                    }
                }
            }
            }
    }

    public FIFO initailFIFO() {
        pageTable p = initialPageTable();
        FIFO fifo = new FIFO(5);
        fifo.addFrame(p.getItem(0)); fifo.addFrame(p.getItem(1));
        fifo.addFrame(p.getItem(2)); fifo.addFrame(p.getItem(3));
        return fifo;
    }

    public pageTable initialPageTable() {
        pageTable table = new pageTable(7);
        list_item p0 = new list_item(0,1, 5, 011);
        list_item p1 = new list_item(1,1, 8, 012);
        list_item p2 = new list_item(2,1, 9, 013);
        list_item p3 = new list_item(3,1, 1, 021);
        list_item p4 = new list_item(4,0, -1, 022);
        list_item p5 = new list_item(5,0, -1, 023);
        list_item p6 = new list_item(6,0, -1, 121);
        table.setItem(p0);
        table.setItem(p1);
        table.setItem(p2);
        table.setItem(p3);
        table.setItem(p4);
        table.setItem(p5);
        table.setItem(p6);
        return table;
    }

    public ArrayList<instructions> initialInstructions() {
        ArrayList<instructions> Instructions = new ArrayList<>();
        instructions i1 = new instructions("+", 0, 70);
        instructions i2 = new instructions("+", 1, 50);
        instructions i3 = new instructions(" ", 2, 15);
        instructions i4 = new instructions("存", 3, 21);
        instructions i5 = new instructions("取", 0, 56);
        instructions i6 = new instructions("-", 6, 40);
        instructions i7 = new instructions("移位", 4, 53);
        instructions i8 = new instructions("+", 5, 23);
        instructions i9 = new instructions("存", 1, 37);
        instructions i10 = new instructions("取", 2, 78);
        instructions i11 = new instructions("+", 4, 1);
        instructions i12 = new instructions("存", 6, 84);
        Instructions.add(i1);
        Instructions.add(i2);
        Instructions.add(i3);
        Instructions.add(i4);
        Instructions.add(i5);
        Instructions.add(i6);
        Instructions.add(i7);
        Instructions.add(i8);
        Instructions.add(i9);
        Instructions.add(i10);
        Instructions.add(i11);
        Instructions.add(i12);
        return Instructions;
    }

    class instructions {
        private String code;
        private int pages;
        private int offset;

        public instructions(String code, int pages, int offset) {
            this.code = code;
            this.pages = pages;
            this.offset = offset;
        }
    }

    class list_item {
        private int sign;
        private int blocks;
        private int disk_add;
        private int dirty_bit = 0;
        private int pages;
        public list_item(int pages,int sign, int blocks, int disk_add) {
            this.pages=pages;
            this.sign = sign;
            this.blocks = blocks;
            this.disk_add = disk_add;
        }

        public void setBlocks(int blocks) {
            this.blocks = blocks;
        }

        public void setSign(int sign){
            this.sign = sign;
        }
        public void setDirty_bit(int dirtyBit) {
            dirty_bit = dirtyBit;
        }

        public int getDirty_bit() {
            return dirty_bit;
        }

        public int getPages() {
            return pages;
        }
    }

    class pageTable {
        private list_item[] elements;

        public pageTable(int length) {
            elements = new list_item[length];
        }

        public void setItem(list_item item) {
            for (int i = 0; i < elements.length; i++) {
                if (elements[i] == null) {
                    elements[i] = item;
                    return;
                }
            }
        }

        public list_item getItem(int i){
            return elements[i];
        }

    }

    class FIFO {
        private int front;
        private int rear;
        private list_item[] listItems;
        public FIFO(int num) {
            listItems = new list_item[num];
            front = rear = 0;
        }

        public void clear() {
            front = rear = 0;
        }

        public boolean isEmpty() {
            return front == rear;
        }

        public boolean isFull() {
            return (rear + 1) % listItems.length == front;
        }

        public void addFrame(list_item No) {
            if (isFull()) throw new IndexOutOfBoundsException();
            rear = (rear + 1) % listItems.length;
            listItems[rear] = No;
        }

        public list_item pop() {
            if (isEmpty()) throw new IndexOutOfBoundsException();
            front = (front + 1) % listItems.length;
            return listItems[front];
        }

        public list_item getItems(int pages){
            for(int i=0;i<listItems.length;i++){

                if (listItems[i] !=null&&listItems[i].pages == pages){
                    return listItems[i];
                }
            }
            return null;
        }


        public void cover(list_item No) {
            listItems[(front + 1) % listItems.length] = No;
            front = (front + 1) % listItems.length;
            rear = (rear + 1) % listItems.length;
    }
    }



}
