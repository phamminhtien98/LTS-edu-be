package com.quanlyphattu.common;

import com.google.gson.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class CommonUtil {
    public static <E> E jsonToObject(String json, Class<E> clazz) {
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
            @Override
            public LocalDate deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return LocalDate.parse(jsonElement.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
        })
                .registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
                    @Override
                    public LocalDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                        return LocalDateTime.parse(jsonElement.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    }
                })
                .create();
        E e = gson.fromJson(json, clazz);
        return e;
    }

    public static <E> List<E> jsonToList(String json, Class<E[]> clazz) {
        return Arrays.asList(new GsonBuilder().create().fromJson(json, clazz));
    }

    public static <E> List<String> validator(E object) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<E>> violations = validator.validate(object);

        List<String> stringList = new ArrayList<String>();
        if (!violations.isEmpty()) {
            for (ConstraintViolation<E> x : violations) {
                StringBuilder errorMessage = new StringBuilder();
                String fieldErr = x.getPropertyPath().toString() + ": ";
                errorMessage.append(fieldErr)
                        .append(x.getMessage());
                stringList.add(errorMessage.toString());
            }
        }
        return stringList;
    }

    public static String chuanHoaTen(String name) {
        StringBuilder result = new StringBuilder();
        String[] st = name.trim().split(" ");
        for (String s : st) {
            if (!s.equals(""))
                result.append(Character.toUpperCase(s.charAt(0))).append(s.substring(1).toLowerCase()).append(" ");
        }
        return result.toString().trim();
    }

    public static enum EGioiTinh {
        NAM(1, "Nam"),
        NU(2, "Nữ");
        private final int code;
        private final String name;

        private EGioiTinh(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    public static enum ETrangThaiDon {
        DANG_CHO(1, "Đang chờ"),
        DONG_Y(2, "Đồng ý"),
        KHONG_DONG_Y(3, "Không đồng ý");
        private final int code;
        private final String name;

        private ETrangThaiDon(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    public static enum EHoanTuc {
        DA_HOAN_TUC(1, "Đã hoàn tục"),
        CHUA_HOAN_TUC(0, "Chưa hoàn tục");
        private final int code;
        private final String name;

        private EHoanTuc(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    public static enum EThamGia {
        THAM_GIA(1, "Tham gia"),
        KHONG_THAM_GIA(0, "Không tham gia");
        private final int code;
        private final String name;

        private EThamGia(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    public static enum EKieuThanhVien {
        ADMIN(1, "admin"),
        PHAT_TU(2, "Phật Tử");
        private final int code;
        private final String name;

        private EKieuThanhVien(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

}
