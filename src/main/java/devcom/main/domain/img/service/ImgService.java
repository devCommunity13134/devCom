package devcom.main.domain.img.service;

import devcom.main.domain.category.entity.Category;
import devcom.main.domain.img.entity.Img;
import devcom.main.domain.img.repository.ImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImgService {
    private final ImgRepository imgRepository;

    //img create
    public void create(String imgUrl){
        Img img = Img.builder()
                .img_url(imgUrl)
                .build();

        this.imgRepository.save(img);
    }

    //img modify
    public void modify(Img img, String imgUrl){
        Img img1 = img.toBuilder()
                .img_url(imgUrl)
                .build();

        this.imgRepository.save(img1);
    }

    //img delete
    public void delete(Img img){
        this.imgRepository.delete(img);
    }
}
