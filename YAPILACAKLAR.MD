# SOCIAL MEDIA APPLICATION

## Yapılacak Listesi 1

1. Dependency Management için gerekli ayarlamalar yapılmalı.
   dependencies.gradle dosyası hazırlanmalı.
   libs kısmında gerekli görülen bağımlılıklar build.gradle(ROOT) içinde tüm projelerde kullanılacak olanlar çağrılmalı.
2. Projede kullanacağımız sürümler önceki proje ile aynı olsun.
3. İçerisine şimdilik sadece AuthService modülü ekleyeceğiz.
4. Auth entity olacak. ( username,password,email,ROLE(enum, ADMIN,USER), activationCode, Status (Enum,
   Active,Deleted,Pending,Banned) )
5. activationCode: 5 haneli bir kod oluşturup onu kullanıcıya dönmek istiyoruz. Rastgele bir kod üretilmeli.
   UUID oluşturalım. UUID.randomUUID().toString()
   Gelen örnek UUID: e58ed763-928c-4155-bee9-fdbaaadc15f3
   UUID 5 bölümden oluuyor. Her bölümün ilk karakterini alıp bizim activation codumuzu(e94bf) dönecek bir metod yazalım.
   Bu metod util altında CodeGenerator adlı sınıf içinde static bir metod olsun.
   Truncate? -> Sadece tablo içindeki verileri silmek için kullanılıyordu.
6. Exceptionları uygun şekilde kullanalım.
7. Register içinde requestDto-> username,password,repassword,email, responseDto->username, Mapper kullanalım.
8. Validation İşlemleri için ilgili bağımlılık eklenecek.
   Ve Register olurken Validation işlemleri yapılacak.
   username ve password boş geçilemez. min:3 max:20 karakter olabilir.
   email validasyonu da ekleyelim. Email de boş geçilmez.
   @NotNull, @NotBlank, @NotEmpty

9. Login işlemleri olacak. request-> email,password, response-> (id,username,email,role,status)

10. Statusu aktif olmayan login yapamamalı.
11. hesabı aktif etmeliyiz. Aktif ederken hangi bilgilere ihtiyacımız var? request->id,activationCode bilgisi alıp,
    gerekli kontrollerden(bu idye ait bir user biz de var mı?, bu activation code bu usera mı ait? değilse hata fırlat.)
    sonra statusu ACTIVE duruma update etmeliyiz. response-> "Aktivasyon Başarılı! Sisteme giriş yapabilirsiniz."

12. Activasyon işlemi STATUS'u sadece PENDING olanlar için geçerli olmalıdır. Diğer durumlarda uygun mesajlar dönülmeli.
13. Bir auth'u silecek endpoint yazınız. Hard Delete-> Gerçekten kaydı silmek. Soft Delete-> Statusu DELETED yapacağız.
    Dışarıdan endpointe id'si gönderilen auth'un Statusunu DELETED yapacağız.
    PathVariable kullanalım.

## Todo List 2 13.05.2024

1. JWT token mekanizmasını da kullanmalıyız.
   Login olan kullanıcıya token dönmeliyiz. İşlemleri adım adım yapınız.

2. Token oluşturmak için ; JWTTokenManager içersinde:

* id alıp token oluşturan metod,
* id ve role alıp token oluşturan metodu yazalım.

3. decode için getIdFromToken metodunu yazalım.

4. decode için getRoleFromToken metodunu yazalım.

5. secretKey ve issuer bilgileri enviroment variablestan gelsin. Öncesinde application.yml dosyasına çekilmesi
   gerektiğini unutmayınız.

6. AuthControllerda bu 4 metodu denemek için endpointler açalım.

* getToken endpointi id alacak token yaratacak.
* getRoleToken endpointi id ve role alacak token yaratacak.
* getIdFromToken endpointi token alacak id verecek.
* getRoleFromToken endpointi token alacak role string olarak verecek.

7. AuthService'de login metodu başarılı ise token dönecek. token oluştururken id ve role bilgisi alacak bir metodunuz
   olmalı.

8. Token üretme aşamasında hatalar için uygun errorlar fırlatalım.

9. UserService modülü ekleyelim. Gerekli packageları alıp gerekli değişikleri yapınız.

10. Entity açalım. UserProfile (id,authId,username,email,phone,photo,address,about,status(enum))

11. application.yml'da ayarlamaları yapalım. db olarak docker içindeki mongoyu kullanalım.

12. Repository katmanını oluşturalım.
13. Service katmanını oluşturalım.
14. Controller katmanını oluşturalım.

15. Controller içinde save metodu olsun. UserSaveRequestDto ile kayıt almalıyız.  (authId,username,email)

16. Dönüşümler için mapper kullanın. UserMapper.

17. Authta kayıt olan user için userservice de save metodu tetiklenmeli. OpenFeign kullanarak bu işlemi
    gerçekleştiriniz.

18. Araştırma: Eğer auth'a kaydedip user'a kaydedemezse authdaki kaydıda silmesini nasıl sağlayabiliriz?

19. En sonunda projenin tamamını deneyelim. Çalıştığından emin olalım.

## Todo List 3 14.05.2024

1. NoSQL ve SQL farkı?
   NoSQL ve SQL farkı: SQL veritabanları tablo bazlı yapıdadır ve genellikle ilişkisel veritabanlarıdır, NoSQL
   veritabanları ise belge, anahtar-değer, grafik veya geniş sütun depoları gibi farklı yapılarda olabilir ve ilişkisel
   olmayan verileri saklamak için kullanılır.

2. NoSQL db tipleri nelerdir? Ve altındaki databaseler nelerdir?
   NoSQL db tipleri ve altındaki veritabanları:

   Belge Tabanlı (Document-Based): MongoDB, CouchDB
   Anahtar-Değer (Key-Value): Redis, DynamoDB
   Graf Tabanlı (Graph-Based): Neo4j, OrientDB
   Geniş Sütunlu (Wide-Column): Cassandra, HBase

3. CAP teoremi nedir?
   CAP Teoremi (Brewer's Theorem), Eric Brewer tarafından önerilen ve dağıtık sistemler için üç temel özellik arasında
   bir denge olduğunu belirten bir teoridir. Bu özellikler:

   Consistency (Tutarlılık): Her okuma işlemi, en güncel yazma işlemini yansıtmalıdır. Yani, sistemdeki tüm düğümler
   aynı
   veriyi görmelidir.

   Availability (Erişilebilirlik): Her istek (okuma veya yazma), her zaman bir yanıt almalıdır (başarılı veya
   başarısız).
   Yani, sistem her zaman çalışır durumda olmalıdır.

   Partition Tolerance (Bölünme Toleransı): Sistem, ağ bölünmeleri veya düğümler arası iletişim hataları olduğunda bile
   çalışmaya devam etmelidir.

   CAP Teoremi'ne göre, bir dağıtık sistem aynı anda bu üç özelliği tam olarak sağlayamaz; en fazla iki tanesini
   sağlayabilir. Örneğin:

   CP (Consistency + Partition Tolerance): Sistem tutarlıdır ve bölünmelere toleranslıdır, ancak bazı durumlarda
   erişilebilir olmayabilir. Örneğin, HBase.
   AP (Availability + Partition Tolerance): Sistem erişilebilirdir ve bölünmelere toleranslıdır, ancak tutarlılığı feda
   edebilir. Örneğin, Cassandra.
   CA (Consistency + Availability): Sistem tutarlıdır ve erişilebilirdir, ancak bölünmelere toleranslı değildir. Bu tür
   sistemler genellikle tek bir veri merkezi içinde çalışır.

4. ACID nedir?
   ACID, veritabanı işlemlerinin güvenilir ve tutarlı olmasını sağlayan dört temel özelliği tanımlar:

   Atomicity (Atomiklik): Bir işlem, ya tamamen gerçekleştirilir ya da hiç gerçekleştirilmez. Yani, işlem ya başarılı
   olur
   ve tüm adımları tamamlanır ya da herhangi bir adım başarısız olursa hiçbir adım gerçekleştirilmemiş gibi geri alınır.

   Consistency (Tutarlılık): Bir işlem, veritabanını geçerli bir durumdan başka bir geçerli duruma dönüştürmelidir.
   Yani,
   işlem öncesinde ve sonrasında veritabanı kurallarına uygun olmalıdır.

   Isolation (İzolasyon): Birden fazla işlem aynı anda çalıştığında, her işlem diğerlerinden bağımsız olarak
   yürütülmelidir. Bu, işlemlerin ara durumlarının birbirini etkilememesini sağlar.

   Durability (Dayanıklılık): Bir işlem başarıyla tamamlandıktan sonra, sonuçları kalıcı olmalıdır ve sistem
   hatalarından
   korunmalıdır. Yani, işlem sonrası veriler kaybolmamalıdır.

   Bu özellikler, özellikle finansal ve kritik sistemlerde verilerin tutarlılığını ve güvenilirliğini sağlamak için
   önemlidir.

5. AuthService de register olduktan sonra activasyon işlemleri yapılırken bu işlem UserProfile tarafına yansıtılmalı. (
   openfeign) UserProfile tarafında endpointte pathVariable ile authid alabilirsiniz. UserService kapalı veya bir
   nedenden işlem gerçekleşemezse authservicede de bu işlem geriye alınmalı.
6. Login olduktan sonra dönen token ile beraber UserProfile'ını güncelleyebilmeli.
7. Ama burada sadece username değiştirememeli. Onun dışındaki alanlar değişmeli. Örnek olarak: email bilgisini
   değiştirirse auth'da da değişmeli.
8. Update ederken eğer bir değer verilmiyorsa, eski değerler kalsın.
9. Authdaki silme UserProfile tarafına yansıtacak bir mekanizma geliştirin.

## Todo List 4 15.05.2024

1. @RestController ile @Controller farkı?
2. Spring Boot Uygulaması ayağa kalkarken neler oluyor?
3. RestApi yazarken nelere dikkat edilmeli?
4. Richardson Maturity Model nedir?
5. @SpringBootApplication anotasyonlu sınıfı spring package içine alırsam proje çalışır mı? çalışmaz ise neden?
6. Sisteme ConfigServer dahil ediniz. (Local ve Git(ayrı ir repo oluşturarak))
7. Bütün microserviceler yml dosyarını bu configserverdan çekecek şekilde ayarlanmalıdır.
8. git config-server daki bir yml deki değişiklikten servisin haberdan olması için neler yapılabilir? (Araştırma)
   yapmadan

```bash
docker run -d --hostname my-rabbit --name some-rabbit -p 15672:15672 -p 5672:5672 rabbitmq:3-management
```

## Todo list 5 16.05.2024 
1. PostService yazınız. Bir user login olduktan sonra postservice aracılığı ile post atabilmelidir.
2. User kendi attığı postları listeleyebilecek endpointleri olmalı
3. User atılan tüm postları da görebileceği bir endpoint olmalı
4. User kendi attığı postu silmeli
5. Database siz belirleyin. (Mongo veya PostgreSql)










