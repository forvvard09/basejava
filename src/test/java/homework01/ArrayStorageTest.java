package homework01;

/**
 * Test for ArrayStorage.
 *
 * @author Sergei Poddubniak (forvvard09@gmail.com)
 * @version 2.0
 * @since 21.02.2019
 */
public class ArrayStorageTest {


    public static void main(String[] args) {
        ArrayStorage arrayStorage = new ArrayStorage(3);
        arrayStorage.save(new Resume("1","test1" ));
        arrayStorage.save(new Resume("2","test2" ));
        arrayStorage.save(new Resume("3","test3" ));


        //we receive a message that the storage is full
        arrayStorage.save(new Resume("4","test4" ));
        MainTestArrayStorage.printAll(arrayStorage);

        //test update method
        Resume newResume = new Resume("1", "newDescription");
        Resume newTestResume = new Resume("333", "newDescription");
        System.out.println();

        //message is not search resume
        arrayStorage.update(newTestResume);

        //update resume
        arrayStorage.update(newResume);

        MainTestArrayStorage.printAll(arrayStorage);
    }
}
