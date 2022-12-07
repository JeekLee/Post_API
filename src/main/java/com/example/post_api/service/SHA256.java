package com.example.post_api.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// * Spring Bean을 사용하는 것과, Static을 사용하는 것의 차이를 공부하기 위해 내버려둠
// (어짜피 싱글톤이나 스태틱이나 저장 영역만 다른거 아님? 이라는 질문에서 출발)

// Spring 환경에서 Static을 지양해야하는 이유들
// 1. Spring의 도움을 받을 수 없다.
//    Spring에 의해 객체가 관리되는 것은 분명한 장점을 지닌다. JAVA에선 C에서 malloc 써놓고 free 하듯이 사용하는게 불가능하다.
//    왜냐하면 GC가 알아서 Heap 영역을 관리하기 때문.
//    하지만 Spring에선 어노테이션을 통해 Heap에서 객체를 제거할 수도, 제거 전에 로직을 추가할 수도 있다.
// 2. Spring의 기능을 사용할 수 없다.
//    JWT의 Key값을 DB에서 받아와서, 12시를 기준으로 변경해야할 필요가 있다고 생각해보자.
//    Static으로 선언된 Method들은 DB에 접근하기 위해서 개고생을 해야할 것이다.
// 3. Static은 Overriding에 제한적이고, 다형적인 구성이 어려우며 역할이 아닌 책임에 의존해야한다.
//    아래 코드에서 암호화를 SHA256이 아닌 다른 알고리즘으로 해야할 경우가 생긴다면 일일이 Service단을 찾아가 Class를 변경해야함.
//    Encrypter라는 Interface를 선언해두고, Service 계층에서 의존성 주입을 받으면 좀 더 객체 지향적으로 구성할 수 있음.
public class SHA256 {
    public static String encrypt(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());

        return bytesToHex(md.digest());
    }

    static private String bytesToHex(byte[] digest) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            builder.append(String.format("%02x", digest[i]));
        }
        return builder.toString();
    }
}
