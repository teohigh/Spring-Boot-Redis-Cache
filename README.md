# Spring Data Redis

## Lý thuyết

### 1. Redis là gì?

`Redis (Remote Dictionary Server)` là một cơ sở dữ liệu NoSQL lưu trữ dữ liệu dưới dạng key-value. Nó hỗ trợ nhiều cấu trúc dữ liệu như strings, hashes, lists, sets, sorted sets, bitmaps, hyperloglogs, và geospatial indexes.

### 2. Tại sao sử dụng Redis Cache?

- `Hiệu suất cao`: Redis lưu trữ dữ liệu trong bộ nhớ, giúp truy xuất dữ liệu nhanh chóng.
- `Hỗ trợ nhiều cấu trúc dữ liệu`: Redis hỗ trợ nhiều loại cấu trúc dữ liệu, giúp linh hoạt trong việc lưu trữ và truy xuất dữ liệu.
- `Khả năng mở rộng`: Redis có thể được cấu hình để chạy trên nhiều máy chủ, giúp tăng khả năng mở rộng và độ tin cậy.

### 3. Các khái niệm cơ bản trong Redis Cache

- `Key-Value Store`: Redis lưu trữ dữ liệu dưới dạng cặp key-value.
- `TTL (Time to Live)`: Redis cho phép thiết lập thời gian sống cho mỗi key, sau thời gian này key sẽ tự động bị xóa.
- `Persistence`: Redis hỗ trợ lưu trữ dữ liệu trên đĩa để đảm bảo dữ liệu không bị mất khi hệ thống gặp sự cố.

### 4. Các lệnh cơ bản trong Redis

- `SET key value`: Lưu trữ giá trị với key.
- `GET key`: Lấy giá trị của key.
- `DEL key`: Xóa key.
- `EXPIRE key seconds`: Thiết lập thời gian sống cho key.
- `FLUSHALL`: Xóa tất cả dữ liệu trong Redis.

### 5. Cấu hình Redis trong Spring Boot

- Dependency: Thêm dependency `spring-boot-starter-data-redis` vào pom.xml.
- Cấu hình: Thiết lập thông tin kết nối Redis trong `application.properties`.

### 6. Các annotation trong Spring Cache

- `@Cacheable`: Đánh dấu phương thức để lưu kết quả vào cache.
- `@CachePut`: Cập nhật cache sau khi phương thức được thực thi.
- `@CacheEvict`: Xóa dữ liệu khỏi cache.

### 7. Quản lý cache

- Cache Manager: Spring Boot cung cấp `CacheManager` để quản lý các cache.
- Cache Names: Đặt tên cho các cache để dễ dàng quản lý và truy xuất.

### 8. Các vấn đề thường gặp

- `Cache Miss`: Khi dữ liệu không có trong cache, hệ thống phải truy xuất dữ liệu từ nguồn gốc (database).
- `Cache Invalidation`: Khi dữ liệu trong cache không còn hợp lệ, cần phải xóa hoặc cập nhật cache.
- `Cache Consistency`: Đảm bảo dữ liệu trong cache luôn đồng bộ với dữ liệu gốc.

### 9. Tối ưu hóa Redis Cache

- Sử dụng TTL: Thiết lập thời gian sống cho các key để tránh cache bị đầy.
- Phân vùng dữ liệu: Sử dụng nhiều cache để phân vùng dữ liệu, giúp tăng hiệu suất.
- Giám sát và quản lý: Sử dụng các công cụ giám sát để theo dõi hiệu suất và tình trạng của Redis.

### 10. Bảo mật Redis

- `Authentication`: Thiết lập mật khẩu cho Redis để ngăn chặn truy cập trái phép.
- `Firewall`: Sử dụng firewall để giới hạn truy cập vào Redis chỉ từ các IP được phép.

## Cài đặt

### 1. Cài đặt Redis

- Tải và cài đặt Redis từ [redis.io](https://redis.io/download).
- Khởi động Redis server.

### 2. Thêm dependency vào `pom.xml`

```xml
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-redis</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-cache</artifactId>
		</dependency>
```

### 3. Cấu hình Redis trong `application.properties`

```xml
# Redis configuration
spring.data.redis.host=localhost
spring.data.redis.port=6379

# Enable caching
spring.cache.type=redis
```

### 4. Kích hoạt caching trong Spring Boot

```java
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class AppConfig {
}
```

### 5. Sử dụng Redis Cache trong mã nguồn

```java
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Cacheable("users")
    public User getUserById(Long id) {
        // Giả lập truy vấn dữ liệu từ database
        return new User(id, "User" + id);
    }
}
```

## Tham khảo

Để sử dụng Redis Cache trong mã nguồn, bạn có thể sử dụng các annotation như `@Cacheable`, `@CachePut`, và `@CacheEvict`. Dưới đây là sự khác nhau giữa chúng:

- `@Cacheable`: Annotation này được sử dụng để đánh dấu một phương thức mà kết quả của nó sẽ được lưu vào cache. Nếu cache đã có dữ liệu cho key tương ứng, phương thức sẽ không được thực thi mà trả về dữ liệu từ cache.

- `@CachePut`: Annotation này được sử dụng để cập nhật cache mà không bỏ qua việc thực thi phương thức. Nó đảm bảo rằng cache luôn có dữ liệu mới nhất.

- `@CacheEvict`: Annotation này được sử dụng để xóa dữ liệu khỏi cache. Nó có thể được sử dụng để xóa một hoặc nhiều entry trong cache.

Dưới đây là ví dụ minh họa:

```java
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Cacheable("users")
    public User getUserById(Long id) {
        // Giả lập truy vấn dữ liệu từ database
        return new User(id, "User" + id);
    }

    @CachePut(value = "users", key = "#user.id")
    public User updateUser(User user) {
        // Cập nhật dữ liệu người dùng
        return user;
    }

    @CacheEvict(value = "users", key = "#id")
    public void deleteUser(Long id) {
        // Xóa người dùng khỏi database
    }
}
```

Giải thích chi tiết:

- `@Cacheable("users")`: Khi phương thức getUserById được gọi, kết quả sẽ được lưu vào cache với key là users::id. Nếu phương thức được gọi lại với cùng id, kết quả sẽ được lấy từ cache thay vì thực thi lại phương thức.

- `@CachePut(value = "users", key = "#user.id")`: Khi phương thức updateUser được gọi, cache sẽ được cập nhật với key là users::user.id và giá trị mới.

- `@CacheEvict(value = "users", key = "#id")`: Khi phương thức deleteUser được gọi, entry trong cache với key là users::id sẽ bị xóa.

`Serializable` là một interface trong Java được sử dụng để đánh dấu các lớp có thể được tuần tự hóa (serialization). Tuần tự hóa là quá trình chuyển đổi trạng thái của một đối tượng thành một chuỗi byte để có thể lưu trữ đối tượng đó vào file, cơ sở dữ liệu, hoặc truyền qua mạng.

Tại sao cần sử dụng `Serializable`?

- Lưu trữ đối tượng: Bạn có thể lưu trữ trạng thái của đối tượng vào file hoặc cơ sở dữ liệu và khôi phục lại sau này.
- Truyền đối tượng qua mạng: Bạn có thể truyền đối tượng qua mạng giữa các ứng dụng hoặc giữa các thành phần của một ứng dụng phân tán.

Cách sử dụng `Serializable`

Để một lớp có thể được tuần tự hóa, lớp đó cần phải implement interface `Serializable`. Interface này không có phương thức nào, nó chỉ là một dấu hiệu cho trình biên dịch và JVM biết rằng lớp này có thể được tuần tự hóa.
