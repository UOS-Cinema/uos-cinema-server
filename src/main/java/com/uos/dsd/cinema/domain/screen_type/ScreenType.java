package com.uos.dsd.cinema.domain.screen_type;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.uos.dsd.cinema.domain.price.constaint.PriceConstraint;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "screen_types")
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class ScreenType {

    @Id
    private String type;

    private String iconUrl;
    
    private int price;

    /**
     * 단순 참조를 위한 ScreenType 객체를 생성합니다.
     * 이 메서드는 다대다 관계에서 참조용으로만 사용되어야 합니다.
     * 
     * @param type 스크린 타입 식별자
     * @return 참조용 ScreenType 객체
     */
    public static ScreenType reference(String type) {
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("Type is required");
        }
        return new ScreenType(type);
    }

    public ScreenType(
            String type, 
            String iconUrl, 
            int price) {

        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("Type is required");
        }
        PriceConstraint.validatePrice(price);

        this.type = type;
        this.iconUrl = iconUrl;
        this.price = price;
    }
    
    private ScreenType(String type) {
        this.type = type;
    }
}
