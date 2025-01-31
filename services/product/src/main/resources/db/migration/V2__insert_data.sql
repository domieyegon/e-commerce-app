-- Insert data into category
INSERT INTO category (id, name, description)
VALUES
    (nextval('category_seq'), 'Electronics', 'Devices and gadgets'),
    (nextval('category_seq'), 'Furniture', 'Home and office furniture'),
    (nextval('category_seq'), 'Clothing', 'Apparel and accessories'),
    (nextval('category_seq'), 'Groceries', 'Food and household essentials'),
    (nextval('category_seq'), 'Automotive', 'Car accessories and parts');

-- Insert data into product
INSERT INTO product (id, name, description, available_quantity, price, category_id)
VALUES
    -- Electronics
    (nextval('product_seq'), 'Laptop', '15-inch gaming laptop', 10, 1200.00, (SELECT id FROM category WHERE name = 'Electronics')),
    (nextval('product_seq'), 'Smartphone', 'Latest model with high-resolution camera', 20, 800.00, (SELECT id FROM category WHERE name = 'Electronics')),
    (nextval('product_seq'), 'Wireless Earbuds', 'Noise-canceling earbuds', 30, 150.00, (SELECT id FROM category WHERE name = 'Electronics')),
    (nextval('product_seq'), 'Smartwatch', 'Fitness tracking smartwatch', 25, 200.00, (SELECT id FROM category WHERE name = 'Electronics')),

    -- Furniture
    (nextval('product_seq'), 'Office Chair', 'Ergonomic office chair', 15, 150.00, (SELECT id FROM category WHERE name = 'Furniture')),
    (nextval('product_seq'), 'Dining Table', 'Wooden dining table for six', 5, 500.00, (SELECT id FROM category WHERE name = 'Furniture')),
    (nextval('product_seq'), 'Sofa Set', 'Luxury 3-piece sofa set', 3, 1200.00, (SELECT id FROM category WHERE name = 'Furniture')),
    (nextval('product_seq'), 'Bookshelf', '5-tier wooden bookshelf', 12, 100.00, (SELECT id FROM category WHERE name = 'Furniture')),

    -- Clothing
    (nextval('product_seq'), 'T-Shirt', 'Cotton round-neck t-shirt', 50, 20.00, (SELECT id FROM category WHERE name = 'Clothing')),
    (nextval('product_seq'), 'Jeans', 'Denim jeans for men', 40, 40.00, (SELECT id FROM category WHERE name = 'Clothing')),
    (nextval('product_seq'), 'Jacket', 'Winter jacket with hood', 20, 80.00, (SELECT id FROM category WHERE name = 'Clothing')),
    (nextval('product_seq'), 'Sneakers', 'Casual running sneakers', 25, 90.00, (SELECT id FROM category WHERE name = 'Clothing')),

    -- Groceries
    (nextval('product_seq'), 'Rice (5kg)', 'Premium long-grain rice', 100, 10.00, (SELECT id FROM category WHERE name = 'Groceries')),
    (nextval('product_seq'), 'Cooking Oil (1L)', 'Sunflower cooking oil', 80, 5.00, (SELECT id FROM category WHERE name = 'Groceries')),
    (nextval('product_seq'), 'Bread', 'Whole wheat bread', 60, 2.50, (SELECT id FROM category WHERE name = 'Groceries')),
    (nextval('product_seq'), 'Milk (1L)', 'Fresh cow milk', 75, 1.50, (SELECT id FROM category WHERE name = 'Groceries')),

    -- Automotive
    (nextval('product_seq'), 'Car Battery', 'Maintenance-free car battery', 10, 100.00, (SELECT id FROM category WHERE name = 'Automotive')),
    (nextval('product_seq'), 'Engine Oil (5L)', 'Synthetic engine oil', 30, 40.00, (SELECT id FROM category WHERE name = 'Automotive')),
    (nextval('product_seq'), 'Car Wash Shampoo', 'Foaming car shampoo', 50, 8.00, (SELECT id FROM category WHERE name = 'Automotive')),
    (nextval('product_seq'), 'Wiper Blades', 'Universal fit wiper blades', 40, 15.00, (SELECT id FROM category WHERE name = 'Automotive'));
