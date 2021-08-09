package com.ecommerce.control;

import com.ecommerce.dao.DAO;
import com.ecommerce.entity.Account;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet(name = "AddProductControl", value = "/add-product")
@MultipartConfig
public class AddProductControl extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get product information from request.
        String productName = request.getParameter("product-name");
        Double productPrice = Double.valueOf(request.getParameter("product-price"));
        String productDescription = request.getParameter("product-description");
        int productCategory = Integer.parseInt(request.getParameter("product-category"));

        // Get upload image.
        Part part = request.getPart("product-image");
        System.out.println(part);
        String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
        System.out.println(fileName);
        InputStream inputStream = part.getInputStream();
        System.out.println(inputStream);

        // Get the seller id for product.
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        int sellerId = account.getId();

        // Add product to database.
        DAO dao = new DAO();
        dao.addProduct(productName, inputStream, productPrice, productDescription, productCategory, sellerId);
        response.sendRedirect("product-management");
    }
}
