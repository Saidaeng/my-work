package com.thantawan.ThantawanBookStore.repository;

import com.thantawan.ThantawanBookStore.WebAlgorithm.GenerateRandomID;
import com.thantawan.ThantawanBookStore.WebAlgorithm.PictureHash;
import com.thantawan.ThantawanBookStore.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Repository
public class JdbcBookRepository implements BookRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean existsByBookID(String bookID) {
        String sql = "SELECT COUNT(*) FROM Book WHERE BookID = ?";

        Integer count = jdbcTemplate.queryForObject(sql, new Object[] { bookID }, Integer.class);

        return count != null && count > 0;
    }

    @Override
    public Member findByMemberID(String memberID) {
        String sql = "SELECT * FROM Member WHERE MemberID = ?";
        RowMapper<Member> rowMapper = (rs, rowNum) -> {
            Member member = new Member();
            member.setMemberID(rs.getString("MemberID"));
            member.setFname(rs.getString("Fname"));
            member.setLname(rs.getString("Lname"));
            member.setCitizenID(rs.getString("CitizenID"));
            member.setEmail(rs.getString("Email"));
            member.setUsername(rs.getString("Username"));
            member.setPassword(rs.getString("Password"));
            member.setTel(rs.getString("Tel"));
            member.setAbout(rs.getString("About"));
            member.setStreet(rs.getString("Street"));
            member.setDistrict(rs.getString("District"));
            member.setProvince(rs.getString("Province"));
            member.setZipcode(rs.getInt("Zipcode"));
            return member;
        };
        List<Member> members = jdbcTemplate.query(sql, rowMapper, memberID);
        Member result = members.isEmpty() ? null : members.get(0);
        return result;
    }

    @Override
    public Category findByCategoryID(String categoryID) {
        String sql = "SELECT * FROM Category WHERE CID = ?";
        RowMapper<Category> rowMapper = (rs, rowNum) -> {
            Category category = new Category();
            category.setCid(rs.getString("CID"));
            category.setCname(rs.getString("cname"));
            return category;
        };
        List<Category> categories = jdbcTemplate.query(sql, rowMapper, categoryID);
        Category result = categories.isEmpty() ? null : categories.get(0);
        return result;
    }

    @Override
    public String addBook(BookForm bookForm) {
        String sqlBook = "INSERT INTO Book (BookID, LessorID, Tagline, Status, B_name, ISBN, Full_price, P_time, P_year, Publisher, datebook, Rent_Price_Per_Day, Security_Deposit, CID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlAuthor = "INSERT INTO Author (LessorID, BookID, Author) VALUES (?, ?, ?)";
        String sqlPicture = "INSERT INTO Picture (LessorID, BookID, picture, picture_hash) VALUES (?, ?, ?, ?)";

        String generatedBookId;
        do {
            generatedBookId = GenerateRandomID.generateRandomNumberString(10);
        } while (existsByBookID(generatedBookId));

        if (!existsByBookID(generatedBookId)) {
            Book book = new Book();
            book.setBName(bookForm.getBName());
            book.setBookID(generatedBookId);
            book.setDatebook(bookForm.getDatebook());
            book.setFullPrice(bookForm.getFullPrice());
            book.setIsbn(bookForm.getIsbn());
            book.setLessor(findByMemberID(bookForm.getLessorID()));
            book.setPTime(bookForm.getPTime());
            book.setPublisher(bookForm.getPublisher());
            book.setPYear(bookForm.getPYear());
            book.setStatus(Boolean.FALSE);
            book.setRentPricePerDay(bookForm.getRentPricePerDay());
            book.setSecurityDeposit(bookForm.getSecurityDeposit());
            book.setTagline(bookForm.getTagline());

            jdbcTemplate.update(sqlBook, book.getBookID(), book.getLessor().getMemberID(), book.getTagline(),
                    book.getStatus(), book.getBName(), book.getIsbn(), book.getFullPrice(), book.getPTime(),
                    book.getPYear(), book.getPublisher(), book.getDatebook(), book.getRentPricePerDay(),
                    book.getSecurityDeposit(), bookForm.getCategory());
            List<String> authors = bookForm.getAuthors();
            for (String authorr : authors) {
                jdbcTemplate.update(sqlAuthor, bookForm.getLessorID(), book.getBookID(), authorr);
            }
            List<MultipartFile> images = bookForm.getImages();
            for (MultipartFile image : images) {
                try {
                    byte[] imageBytes = image.getBytes();
                    jdbcTemplate.update(sqlPicture, bookForm.getLessorID(), book.getBookID(), imageBytes,
                            PictureHash.getSha256Hash(imageBytes));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            return "Successfully add book";
        } else {
            return "Error occurred while registering the book";
        }

    }

    @Override
    public List<Book> getRandomBook() {
        String sql = "SELECT * FROM Book ORDER BY RAND() LIMIT 5";
        RowMapper<Book> rowMapper = (rs, rowNum) -> {
            Book book = new Book();
            book.setBookID(rs.getString("BookID"));
            book.setTagline(rs.getString("Tagline"));
            book.setStatus(rs.getBoolean("Status"));
            book.setBName(rs.getString("B_name"));
            book.setIsbn(rs.getInt("ISBN"));
            book.setFullPrice(rs.getBigDecimal("Full_price"));
            book.setPTime(rs.getInt("P_time"));
            book.setPYear(rs.getInt("P_year"));
            book.setPublisher(rs.getString("Publisher"));
            book.setDatebook(rs.getDate("Datebook"));
            book.setRentPricePerDay(rs.getInt("Rent_Price_Per_Day"));
            book.setSecurityDeposit(rs.getInt("Security_Deposit"));
            book.setCost(rs.getInt("Cost"));
            book.setStatusLo(rs.getString("Status_lo"));
            book.setLogicCom(rs.getString("Logic_com"));
            book.setStartDate(rs.getDate("Start_date"));
            book.setEndDate(rs.getDate("End_date"));
            book.setTenant(rs.getString("TenantID"));

            // Call the respective repository methods for lessor and category
            String lessorID = rs.getString("LessorID");
            Member lessor = findByMemberID(lessorID);
            book.setLessor(lessor);

            String categoryID = rs.getString("CID");
            Category category = findByCategoryID(categoryID);
            book.setCategory(category);

            /*List<Author> authors = findAuthorsByBookID(book.getBookID());
            book.setAuthors(authors);

            List<Picture> pictures = findPicturesByBookID(book.getBookID());
            book.setPictures(pictures);*/

            return book;
        };
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<Book> getBookByLessorID(String lessorID) {
        String sql = "SELECT * FROM Book WHERE LessorID = ?";
        RowMapper<Book> rowMapper = (rs, rowNum) -> {
            Book book = new Book();
            book.setBookID(rs.getString("BookID"));
            book.setTagline(rs.getString("Tagline"));
            book.setStatus(rs.getBoolean("Status"));
            book.setBName(rs.getString("B_name"));
            book.setIsbn(rs.getInt("ISBN"));
            book.setFullPrice(rs.getBigDecimal("Full_price"));
            book.setPTime(rs.getInt("P_time"));
            book.setPYear(rs.getInt("P_year"));
            book.setPublisher(rs.getString("Publisher"));
            book.setDatebook(rs.getDate("Datebook"));
            book.setRentPricePerDay(rs.getInt("Rent_Price_Per_Day"));
            book.setSecurityDeposit(rs.getInt("Security_Deposit"));
            book.setCost(rs.getInt("Cost"));
            book.setStatusLo(rs.getString("Status_lo"));
            book.setLogicCom(rs.getString("Logic_com"));
            book.setStartDate(rs.getDate("Start_date"));
            book.setEndDate(rs.getDate("End_date"));
            book.setTenant(rs.getString("TenantID"));

            // Call the respective repository methods for lessor and category
            Member lessor = findByMemberID(lessorID);
            book.setLessor(lessor);

            String categoryID = rs.getString("CID");
            Category category = findByCategoryID(categoryID);
            book.setCategory(category);

            /*List<Author> authors = findAuthorsByBookID(book.getBookID());
            book.setAuthors(authors);

            List<Picture> pictures = findPicturesByBookID(book.getBookID());
            book.setPictures(pictures);*/

            return book;
        };
        return jdbcTemplate.query(sql, rowMapper, lessorID);
    }

    @Override
    public Integer countBook(){
        String sql = "SELECT COUNT(*) FROM Book";

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);

        return count;
    }

    @Override
    public Integer countBookByID(String lessorID){
        String sql = "SELECT COUNT(*) FROM Book WHERE LessorID = ?";

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, lessorID);

        return count;
    }

    @Override
    public List<Picture> findPicturesByBookID(String bookID) {
        String sql = "SELECT * FROM Picture WHERE BookID = ?";
        RowMapper<Picture> rowMapper = (rs, rowNum) -> {
            Picture picture = new Picture();
            picture.setPicture(rs.getBytes("picture"));
            picture.setPictureHash(rs.getString("picture_hash"));

            Book book = getBookByID(bookID);
            picture.setBookId(book.getBookID());
            picture.setLessorId(book.getLessor().getMemberID());

            return picture;
        };
        return jdbcTemplate.query(sql, rowMapper, bookID);
    }
    @Override
    public List<Author> findAuthorsByBookID(String bookID) {
        String sql = "SELECT * FROM Author WHERE BookID = ?";
        RowMapper<Author> rowMapper = (rs, rowNum) -> {
            Author author = new Author();
            author.setAuthor(rs.getString("Author"));

            Book book = getBookByID(bookID);
            author.setBookId(book.getBookID());
            author.setLessorId(book.getLessor().getMemberID());
            return author;
        };
        return jdbcTemplate.query(sql, rowMapper, bookID);
    }

    @Override
    public Book getBookByID(String bookID) {
        String sql = "SELECT * FROM Book Where BookID = ?";
        RowMapper<Book> rowMapper = (rs, rowNum) -> {
            Book book = new Book();
            book.setBookID(rs.getString("BookID"));
            book.setTagline(rs.getString("Tagline"));
            book.setStatus(rs.getBoolean("Status"));
            book.setBName(rs.getString("B_name"));
            book.setIsbn(rs.getInt("ISBN"));
            book.setFullPrice(rs.getBigDecimal("Full_price"));
            book.setPTime(rs.getInt("P_time"));
            book.setPYear(rs.getInt("P_year"));
            book.setPublisher(rs.getString("Publisher"));
            book.setDatebook(rs.getDate("Datebook"));
            book.setRentPricePerDay(rs.getInt("Rent_Price_Per_Day"));
            book.setSecurityDeposit(rs.getInt("Security_Deposit"));
            book.setCost(rs.getInt("Cost"));
            book.setStatusLo(rs.getString("Status_lo"));
            book.setLogicCom(rs.getString("Logic_com"));
            book.setStartDate(rs.getDate("Start_date"));
            book.setEndDate(rs.getDate("End_date"));
            book.setTenant(rs.getString("TenantID"));

            // Call the respective repository methods for lessor and category
            String lessorID = rs.getString("LessorID");
            Member lessor = findByMemberID(lessorID);
            book.setLessor(lessor);

            String categoryID = rs.getString("CID");
            Category category = findByCategoryID(categoryID);
            book.setCategory(category);

            /*List<Author> authors = findAuthorsByBookID(book.getBookID());
            book.setAuthors(authors);

            List<Picture> pictures = findPicturesByBookID(book.getBookID());
            book.setPictures(pictures);*/

            return book;
        };
        return jdbcTemplate.queryForObject(sql, rowMapper, bookID);
    }
}
