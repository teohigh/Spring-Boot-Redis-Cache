package com.example.service;

import com.example.model.User;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Cacheable(value = "users", key = "#id") // Lưu kết quả trả về vào cache với key là id
    //  Nếu có request khác truy cập vào cùng một id thì sẽ lấy kết quả từ cache
    //  không cần phải thực hiện query lên database nữa (cache hit)
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @CachePut(value = "users", key = "#result.id") // Lưu kết quả trả về vào cache với key là id của user
    //  Khi có request truy cập vào id của user đã được lưu trong cache thì sẽ thực hiện query lên database
    //  để lấy dữ liệu mới nhất và lưu vào cache (cache miss)
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @CacheEvict(value = "users", key = "#id") // Xóa dữ liệu khỏi cache với key là id
    // Khi có request truy cập vào id của user đã được xóa khỏi cache thì sẽ thực hiện query lên database
    // để lấy dữ liệu mới nhất và lưu vào cache (cache miss)
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
