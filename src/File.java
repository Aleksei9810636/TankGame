//import java.io.FileWriter;
//import java.io.IOException;
//
//public interface File {
//    interface FileWrite() {
//        FileWriter writer;
//
//        static {
//            try {
//                writer = new FileWriter("file.txt", true);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        try () {
//            // запись всей строки
//            String text = "Hello Gold!";
//            writer.write(text);
//            // запись по символам
//            writer.append('\n');
//            writer.append('E');
//
//            writer.flush();
//        } catch (IOException ex) {
//
//            System.out.println(ex.getMessage() + " это какая то  ошибка в file");
//        }
//    }
//}
//}
