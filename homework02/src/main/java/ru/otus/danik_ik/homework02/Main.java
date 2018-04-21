package ru.otus.danik_ik.homework02;

/**
 * Hello world!
 *
 */
public class Main
{
    /*
        запускать jar сборки, указывая его же в параметре javaagent (см. run.cmd)
     */
    public static void main( String[] args )
        {
            System.out.println(ObjectSizeFetcher.getObjectSize(new String()));
        }
}
