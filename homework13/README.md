# �������� ������� �13

������� war ��� ���������� �� ��-12.� ��������� ��� � DBService ��� Spring beans, ���������� (inject) �� � ��������.� ��������� ��� ���������� �� ������� ��� �������. 

### �����������

������������ ����������� �� �� 11. ��������������, �� 11 ������ ���� �������� � ��������� Maven-�����������:

````cmd
cd ..\homework11
mvn clean source:jar install
cd ..\homework13
````

��� windows ����� ��������� prepare.cmd � ������� �����

��� ������� ���������� ����������� War-����:

````cmd
mvn clean package
````

����� ��������� ���� (root.war) ���������� �� ����� target � ����� webapps ������� jetty

����������� ������ (� ����� � jetty):

````cmd
java -jar start.jar
````

������: http://localhost:8080

� Idea Ultimate ����� ��������� ������ ��� jetty � ������������� �������.

���, ��� DBService (�������� � ��������� � �����) � �������� �������� ��������� ��� Spring beans (��. src\main\resources\SpringBeans.xml)

��������� CacheEngineImpl �������� ���������� �� ��� � ��������� ������ � ���������� ������������, � ������� ��������������� ��� ��� ������ ������������:

````java
public class CacheEngineBean extends CacheEngineImpl<Long, UserDataSet> {
    public CacheEngineBean(int maxElements, long lifeTimeMs, 
                           long idleTimeMs, boolean isEternal) {
        super(maxElements, lifeTimeMs, idleTimeMs, 
                isEternal, CacheHelper.SoftEntryFactory());
    }
}
````


��� �������� ����������� ������������ ���� � ����������� @Autowired � AdminServlet. �������� �������� �� ������������ � AdminServlet, �� ������������� ��� ����, ����� ������ �������� ������ � ������������� ��� ���� ���������� � ��������� ������ (������ ������������ � ������������).

�� ������� �������� ��� ������: login � admin. ��� ����������� ������� �� ������ Admin �������� � ��������������� �� �������� �����������. ��� ����������� ���� ������ ����� �����, ��������� ����� �� 18 ���� �/��� ����

�� �������� /admin ������������, �����, �������, ���������� �� ������������� ����: ���������� �������� � ���������.


