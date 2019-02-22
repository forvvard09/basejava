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

        ArrayStorage arrayStorage = new ArrayStorage();
        for (int i = 0; i < 10000; i++) {
            Resume resume = new Resume();
            resume.setUuid(String.valueOf(i));
            arrayStorage.save(resume);
        }

        System.out.println(arrayStorage.sizeStorage());
        //we receive a message that the storage is full
        arrayStorage.save(new Resume());
        arrayStorage.clear();
        System.out.println(arrayStorage.sizeStorage());


        //test update method
        Resume newResume = new Resume();
        newResume.setUuid("111");
        arrayStorage.save(newResume);

        Resume upadteResume = new Resume();
        upadteResume.setUuid("111");

        Resume singleResume = new Resume();
        singleResume.setUuid("2");

        //message is not search resume
        arrayStorage.update(singleResume);
        //update resume
        arrayStorage.update(upadteResume);

        MainTestArrayStorage.printAll(arrayStorage);
    }

}
