# SOCIAL MEDIA APPLICATION

## Yapılacak Listesi 1
1. Dependency Management için gerekli ayarlamalar yapılmalı.
   dependencies.gradle dosyası hazırlanmalı.
   libs kısmında gerekli görülen bağımlılıklar build.gradle(ROOT) içinde tüm projelerde kullanılacak olanlar çağrılmalı.
2. Projede kullanacağımız sürümler önceki proje ile aynı olsun.
3. İçerisine şimdilik sadece AuthService modülü ekleyeceğiz.
4. Auth entity olacak. ( username,password,email,ROLE(enum, ADMIN,USER), activationCode, Status (Enum, Active,Deleted,Pending,Banned) )
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
   @NotNull,       @NotBlank,      @NotEmpty

9. Login işlemleri olacak. request-> email,password, response-> (id,username,email,role,status)

10. Statusu aktif olmayan login yapamamalı.
11. hesabı aktif etmeliyiz. Aktif ederken hangi bilgilere ihtiyacımız var? request->id,activationCode bilgisi alıp,
    gerekli kontrollerden(bu idye ait bir user biz de var mı?, bu activation code bu usera mı ait? değilse hata fırlat.)
    sonra statusu ACTIVE duruma update etmeliyiz. response-> "Aktivasyon Başarılı! Sisteme giriş yapabilirsiniz."

12. Activasyon işlemi STATUS'u sadece PENDING olanlar için geçerli olmalıdır. Diğer durumlarda uygun mesajlar dönülmeli.
13. Bir auth'u silecek endpoint yazınız. Hard Delete-> Gerçekten  kaydı silmek. Soft Delete-> Statusu DELETED yapacağız.
    Dışarıdan endpointe id'si gönderilen auth'un Statusunu DELETED yapacağız.
    PathVariable kullanalım.

## Todo List 2 13.05.2024

1. JWT token mekanizmasını da kullanmalıyız.
   Login olan kullanıcıya token dönmeliyiz. İşlemleri adım adım yapınız.

2. Token oluşturmak için ;
* id alıp token oluşturan metod,
* id ve role alıp token oluşturan metodu yazalım.

3. decode için getIdFromToken metodunu yazalım.

4. decode için getRoleFromToken metodunu yazalım.

5. secretKey ve issuer bilgileri enviroment variablestan gelsin. Öncesinde application.yml dosyasına çekilmesi gerektiğini unutmayınız.

6. AuthControllerda bu 4 metodu denemek için endpointler açalım.
* getToken endpointi id alacak token yaratacak.
* getRoleToken endpointi id ve role alacak token yaratacak.
* getIdFromToken endpointi token alacak id verecek.
* getRoleFromToken endpointi token alacak role string olarak verecek.

7. AuthService'de login metodu başarı ise token dönecek. token oluştururken id ve role bilgisi alacak bir metodunuz olmalı.

8. Token üretme aşamasında hatalar için uygun errorlar fırlatalım.

9. UserService modülü ekleyelim. Gerekli packageları alıp gerekli değişikleri yapınız.

10. Entity açalım. UserProfile (id,authId,username,email,phone,photo,address,about,status(enum))

11. application.yml'da ayarlamaları yapalım. db olarak docker içindeki mongoyu kullanalım.

12. Repository katmanını oluşturalım.
13. Service katmanını oluşturalım.
14. Controller katmanını oluşturalım.

15. Controller içinde save metodu olsun. UserSaveRequestDto ile kayıt almalıyız.  (authId,username,email,phone,photo,address,about)

16. Dönüşümler için mapper kullanın. UserMapper.

17. Authta kayıt olan user için userservice de save metodu tetiklenmeli. OpenFeign kullanarak bu işlemi gerçekleştiriniz.

18. Araştırma: Eğer auth'a kaydedip user'a kaydedemezse authdaki kaydıda silmesini nasıl sağlayabiliriz?

19. En sonunda projenin tamamını deneyelim. Çalıştığından emin olalım.