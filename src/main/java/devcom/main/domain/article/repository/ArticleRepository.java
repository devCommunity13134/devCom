package devcom.main.domain.article.repository;

import devcom.main.domain.article.entity.Article;
import devcom.main.domain.category.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findAll(Pageable pageable);

    Page<Article> findAllByCategory(Category category, Pageable pageable);

    @Query("select "
            + "distinct a "
            + "from Article a "
            + "left outer join SiteUser u1 on a.author=u1 "
            + "left outer join Answer an on an.originalArticle=a "
            + "left outer join SiteUser u2 on an.author=u2 "
            + "left outer join Reply r on r.originalArticle=a "
            + "left outer join SiteUser u3 on r.author=u3 "
            + "where "
            + "   (a.subject like %:keyword% "
            + "   or a.content like %:keyword% "
            + "   or u1.nickname like %:keyword% "
            + "   or an.content like %:keyword% "
            + "   or u2.nickname like %:keyword% "
            + "   or r.content like %:keyword%"
            + "   or u3.nickname like %:keyword%)"
            + "   and a.category = :category")
    Page<Article> findAllByKeywordAndCategory(@Param("category") Category category, @Param("keyword") String keyword, Pageable pageable);

    @Query("select "
            + "distinct a "
            + "from Article a "
            + "left outer join SiteUser u1 on a.author=u1 "
            + "left outer join Answer an on an.originalArticle=a "
            + "left outer join SiteUser u2 on an.author=u2 "
            + "where "
            + "   (a.subject like %:keyword% "
            + "   or a.content like %:keyword% "
            + "   or u1.nickname like %:keyword% "
            + "   or an.content like %:keyword% "
            + "   or u2.nickname like %:keyword%) ")
    Page<Article> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
