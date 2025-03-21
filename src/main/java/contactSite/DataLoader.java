package contactSite;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(JdbcTemplate jdbcTemplate) {
        return args -> {
            // programmer 테이블 생성
            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS programmer (" +
                    "id VARCHAR(10) PRIMARY KEY, " +
                    "user_id VARCHAR(50) NOT NULL, " +
                    "password VARCHAR(100) NOT NULL, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "birth_date DATE NOT NULL, " +
                    "age INT NOT NULL, " +
                    "email VARCHAR(100) NOT NULL, " +
                    "personal_history INT NOT NULL, " +
                    "self_introduction TEXT, " +
                    "certificate VARCHAR(50), " +
                    "like_count INT DEFAULT 0)");

            // programmer_fields 테이블 생성
            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS programmer_field_name (" +
                    "programmer_id VARCHAR(10) NOT NULL, " +
                    "field_name VARCHAR(50) NOT NULL, " +
                    "PRIMARY KEY (programmer_id, field_name), " +
                    "FOREIGN KEY (programmer_id) REFERENCES programmer(id))");

            // low_data.csv 파일 읽기
            loadUserData(jdbcTemplate);

            // programmer_fields.csv 파일 읽기
            loadProgrammerFieldsData(jdbcTemplate);

            System.out.println("모든 데이터 로드 완료!");
        };
    }

    private void loadUserData(JdbcTemplate jdbcTemplate) {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new ClassPathResource("data.csv").getInputStream()))) {
            String line;
            int count = 0;
            boolean isHeader = true;

            // CSV 파일 파싱
            while ((line = br.readLine()) != null) {
                // 헤더 행은 건너뛰기
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                // CSV 파싱 (쉼표가 포함된 필드 처리)
                List<String> values = parseCSVLine(line);

                try {
                    // 날짜 형식 변환
                    Date birthDate = convertStringToDate(values.get(4));

                    jdbcTemplate.update(
                            "INSERT INTO programmer (id, user_id, password, name, birth_date, age, email, personal_history, self_introduction, certificate, like_count) " +
                                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                            values.get(0), values.get(1), values.get(2), values.get(3), birthDate,
                            Integer.parseInt(values.get(5)), values.get(6), Integer.parseInt(values.get(7)),
                            values.get(8), values.get(9), Integer.parseInt(values.get(10))
                    );
                    count++;

                    // 처리 상황 출력
                    if (count % 1000 == 0) {
                        System.out.println("programmer 데이터 " + count + "개 처리 완료");
                    }
                } catch (Exception e) {
                    System.err.println("사용자 데이터 삽입 오류 (" + values.get(0) + "): " + e.getMessage());
                }
            }
            System.out.println("programmer 테이블에 총 " + count + "개의 행이 로드되었습니다.");
        } catch (Exception e) {
            System.err.println("low_data.csv 파일 읽기 오류: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadProgrammerFieldsData(JdbcTemplate jdbcTemplate) {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new ClassPathResource("programmer_fields.csv").getInputStream()))) {
            String line;
            int count = 0;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                // 헤더 행은 건너뛰기
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] values = line.split(",");

                try {
                    jdbcTemplate.update(
                            "INSERT INTO programmer_field_name (programmer_id, field_name) VALUES (?, ?)",
                            values[0].trim(), values[1].trim()
                    );
                    count++;

                    // 처리 상황 출력
                    if (count % 1000 == 0) {
                        System.out.println("Programmer Fields 데이터 " + count + "개 처리 완료");
                    }
                } catch (Exception e) {
                    System.err.println("프로그래머 필드 데이터 삽입 오류 (" + values[0] + "): " + e.getMessage());
                }
            }
            System.out.println("programmer_fields 테이블에 총 " + count + "개의 행이 로드되었습니다.");
        } catch (Exception e) {
            System.err.println("programmer_fields.csv 파일 읽기 오류: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // 날짜 문자열을 java.sql.Date로 변환하는 메서드
    private Date convertStringToDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = sdf.parse(dateStr);
            return new Date(utilDate.getTime());
        } catch (Exception e) {
            System.err.println("날짜 형식 오류: " + dateStr);
            return null;  // 오류 발생 시 null 반환
        }
    }

    // 쉼표가 포함된 필드를 처리하기 위한 간단한 CSV 파서
    private List<String> parseCSVLine(String line) {
        List<String> values = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder currentValue = new StringBuilder();

        for (char c : line.toCharArray()) {
            if (c == '\"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                values.add(currentValue.toString());
                currentValue = new StringBuilder();
            } else {
                currentValue.append(c);
            }
        }
        values.add(currentValue.toString());

        return values;
    }
}
