-- ------------------------------------
-- Adding Data to SCHEMA `epam_cafe` 
-- ------------------------------------

-- -----------------------------------------------------
-- Table `epam_cafe`.`role`
-- -----------------------------------------------------
INSERT INTO `epam_cafe`.`user_role` (`role_id`, `role`) VALUES ('1', 'customer');
INSERT INTO `epam_cafe`.`user_role` (`role_id`, `role`) VALUES ('2', 'admin');

-- -----------------------------------------------------
-- Table `epam_cafe`.`customer`
-- -----------------------------------------------------
INSERT INTO `epam_cafe`.`user` (`email`, `password`, `first_name`, `last_name`, `phone`, `create_date`, `role_id`) VALUES ('admin@gmail.com', md5('qwQW12'), 'Alex', 'Shirey', '+375(29)612-61-09', '2018-01-02 11:00:00', '2');
INSERT INTO `epam_cafe`.`user` (`email`, `password`, `first_name`, `last_name`, `phone`, `create_date`,`balance`) VALUES ('customer@gmail.com', md5('qwQW12'), 'Jacob', 'Smith', '+375(29)123-45-67', '2018-01-05 12:00:00', '0');
INSERT INTO `epam_cafe`.`user` (`email`, `password`, `first_name`, `last_name`, `phone`, `create_date`,`balance`) VALUES ('sophia_smith@gmail.com', md5('qwertySoph12'), 'Sophia', 'Smtih', '+375(44)555-67-85', '2018-01-06 13:15:00', '50');
INSERT INTO `epam_cafe`.`user` (`email`, `password`, `first_name`, `last_name`, `phone`, `create_date`,`balance`) VALUES ('emily55@gmail.com', md5('Itsmypass12'), 'Emily', 'Johnson', '+375(29)684-55-12', '2018-01-07 17:00:00', '70');
INSERT INTO `epam_cafe`.`user` (`email`, `password`, `first_name`, `last_name`, `phone`, `create_date`,`balance`) VALUES ('lily1983@gmail.com', md5('brownY123'), 'Lily', 'Brown', '+375(29)654-12-32', '2018-01-08 19:00:00', '80');
INSERT INTO `epam_cafe`.`user` (`email`, `password`, `first_name`, `last_name`, `phone`, `create_date`,`balance`) VALUES ('mason_taylor@gmail.com', md5('123maS99'), 'Mason', 'Taylor', '+375(44)887-95-51', '2018-01-10 22:05:00', '90');
INSERT INTO `epam_cafe`.`user` (`email`, `password`, `first_name`, `last_name`, `phone`, `create_date`,`balance`) VALUES ('justlogan@gmail.com', md5('loggyY666'), 'Logan', 'Davis', '+375(33)126-54-58', '2018-01-13 17:10:00', '100');
INSERT INTO `epam_cafe`.`user` (`email`, `password`, `first_name`, `last_name`, `phone`, `create_date`,`balance`) VALUES ('miller87@gmail.com', md5('lucasTTT12'), 'Lucas', 'Miller', '+375(33)612-66-19', '2018-01-17 13:05:00', '20');
INSERT INTO `epam_cafe`.`user` (`email`, `password`, `first_name`, `last_name`, `phone`, `create_date`,`balance`) VALUES ('john_white@gmail.com', md5('ilovemyw1fE'), 'John', 'White', '+375(44)555-98-98', '2018-01-18 11:00:00', '115');
INSERT INTO `epam_cafe`.`user` (`email`, `password`, `first_name`, `last_name`, `phone`, `create_date`,`balance`) VALUES ('hoch_black@gmail.com', md5('johnfromUS!1'), 'John', 'Black', '+375(29)612-67-78', '2018-01-25 11:30:00', '220');
INSERT INTO `epam_cafe`.`user` (`email`, `password`, `first_name`, `last_name`, `phone`, `create_date`,`balance`) VALUES ('christi@gmail.com', md5('chrisTy3389651'), 'Christian', 'Lewis', '+375(44)338-96-51', '2018-01-27 16:55:00', '5');
INSERT INTO `epam_cafe`.`user` (`email`, `password`, `first_name`, `last_name`, `phone`, `create_date`,`balance`) VALUES ('emily99@gmail.com', md5('emmY!_99'), 'Emily', 'Murphy', '+375(29)651-23-98', '2018-01-28 16:09:00', '20');
INSERT INTO `epam_cafe`.`user` (`email`, `password`, `first_name`, `last_name`, `phone`, `create_date`,`balance`) VALUES ('olivia_cooper@gmail.com', md5('iHaveminiCooper1'), 'Olivia', 'Cooper', '+375(44)598-62-32', '2018-01-28 23:59:00', '20');
INSERT INTO `epam_cafe`.`user` (`email`, `password`, `first_name`, `last_name`, `phone`, `create_date`,`balance`) VALUES ('ivanov54@mail.ru', md5('ivanIvanov12'), 'Иван', 'Сидоров', '+375(29)126-13-13', '2018-01-29 11:22:00', '30');
INSERT INTO `epam_cafe`.`user` (`email`, `password`, `first_name`, `last_name`, `phone`, `create_date`,`balance`) VALUES ('zsukZSUK88@gmail.com', md5('iHaveminiCooper1'), 'Андрей', 'Жук', '+375(29)348-52-88', '2018-01-29 13:24:00', '40');

-- -----------------------------------------------------
-- Table `epam_cafe`.`dish_type`
-- -----------------------------------------------------
INSERT INTO `epam_cafe`.`dish_type` (`type`) VALUES ('soup');
INSERT INTO `epam_cafe`.`dish_type` (`type`) VALUES ('salad');
INSERT INTO `epam_cafe`.`dish_type` (`type`) VALUES ('main_dish');
INSERT INTO `epam_cafe`.`dish_type` (`type`) VALUES ('dessert');
INSERT INTO `epam_cafe`.`dish_type` (`type`) VALUES ('hot_drink');
INSERT INTO `epam_cafe`.`dish_type` (`type`) VALUES ('soft_drink');


-- -----------------------------------------------------
-- Table `epam_cafe`.`dish`user
-- -----------------------------------------------------
INSERT INTO `epam_cafe`.`dish` (`type_id`, `name`, `description`, `dish_price`, `in_menu`, `create_date`) VALUES ('1', '3 Cheese Tomato', 'Our signature tomato soup, with cheddar, jack and parmesan cheeses.', '3.50', '1', '2018-01-02 12:00:00');
INSERT INTO `epam_cafe`.`dish` (`type_id`, `name`, `description`, `dish_price`, `in_menu`, `create_date`) VALUES ('1', 'Gazpacho', 'Chilled Spanish-style cucumber, pepper and tomato. Refreshing', '3.00', '1', '2018-01-02 12:00:00');
INSERT INTO `epam_cafe`.`dish` (`type_id`, `name`, `description`, `dish_price`, `in_menu`, `create_date`) VALUES ('1', 'Chicken Posole', 'Delicious roast pork and corn hominy stew, with chiles, lime juice, bell peppers and more', '3.00', '1', '2018-01-02 12:00:00');
INSERT INTO `epam_cafe`.`dish` (`type_id`, `name`, `description`, `dish_price`, `in_menu`, `create_date`) VALUES ('1', 'Curry Red Potato ', 'Chunky and delicious, featuring our own toasted curry spice mix with Okra', '3.00', '1', '2018-01-03 12:00:00');
INSERT INTO `epam_cafe`.`dish` (`type_id`, `name`, `description`, `dish_price`, `in_menu`, `create_date`) VALUES ('1', 'Chef soup', 'Depends on chefs daily desire. Just try it.', '3.50', '1', '2018-01-03 12:00:00');
INSERT INTO `epam_cafe`.`dish` (`type_id`, `name`, `description`, `dish_price`, `in_menu`, `create_date`) VALUES ('2', 'Caesar', 'Classic Caesar salad with chicken. Perfect mix of cream and crunch.', '4.50', '1', '2018-01-02 12:00:00');
INSERT INTO `epam_cafe`.`dish` (`type_id`, `name`, `description`, `dish_price`, `in_menu`, `create_date`) VALUES ('2', 'Cucumber, Black Olive and Mint Salad', 'A chunky salad of cucumbers, cherry tomatoes, peppery mint leaves drizzled with a black olive sauce.', '3.00', '1', '2018-01-05 12:00:00');
INSERT INTO `epam_cafe`.`dish` (`type_id`, `name`, `description`, `dish_price`, `in_menu`, `create_date`) VALUES ('2', 'Carrot Salad with Black Grape Dressing', 'A carrot salad with a freshly made black grape dressing.', '3.00', '1', '2018-01-05 12:00:00');
INSERT INTO `epam_cafe`.`dish` (`type_id`, `name`, `description`, `dish_price`, `in_menu`, `create_date`) VALUES ('2', 'BBQ Potato Salad', 'The good old potato salad with a twist! This one has some cola flavored BBQ sauce as a dressing ingredient.', '3.00', '1', '2018-01-03 12:00:00');
INSERT INTO `epam_cafe`.`dish` (`type_id`, `name`, `description`, `dish_price`, `in_menu`, `create_date`) VALUES ('2', 'Оливье',  'Классический оливье в курицей', '4.00', '1', '2018-01-05 12:00:00');
INSERT INTO `epam_cafe`.`dish` (`type_id`, `name`, `description`, `dish_price`, `in_menu`, `create_date`) VALUES ('3', 'Foiled Fish', 'The filet is herb-crusted, and then cooked in foil with lemon and butter. Served with campfire roasted local squash and corn', '5.50', '1', '2018-01-04 12:00:00');
INSERT INTO `epam_cafe`.`dish` (`type_id`, `name`, `description`, `dish_price`, `in_menu`, `create_date`) VALUES ('3', 'Turkey', 'Ground turkey seasoned with Cajun spices and minced red onion.', '6.00', '1', '2018-01-03 12:00:00');
INSERT INTO `epam_cafe`.`dish` (`type_id`, `name`, `description`, `dish_price`, `in_menu`, `create_date`) VALUES ('3', 'Pasta with Meatballs', 'The ground beef is seasoned \"Italian\" style. Served over pasta with red sauce.', '5.00', '1', '2018-01-04 12:00:00');
INSERT INTO `epam_cafe`.`dish` (`type_id`, `name`, `description`, `dish_price`, `in_menu`, `create_date`) VALUES ('3', 'Pizza', 'Pizza from chef', '5.00', '1', '2018-01-05 12:00:00');
INSERT INTO `epam_cafe`.`dish` (`type_id`, `name`, `description`, `dish_price`, `in_menu`, `create_date`) VALUES ('4', 'Chocolate Chip Cookie', 'Our large chocolate chip cookie!', '3.00', '1', '2018-01-03 12:00:00');
INSERT INTO `epam_cafe`.`dish` (`type_id`, `name`, `description`, `dish_price`, `in_menu`, `create_date`) VALUES ('4', 'Fresh Fruits', 'Start or finish your meal with freshly sliced fruit.  We choose fruits of', '3.00', '1', '2018-01-03 12:00:00');
INSERT INTO `epam_cafe`.`dish` (`type_id`, `name`, `description`, `dish_price`, `in_menu`, `create_date`) VALUES ('4', 'Ice Cream', 'You Scream, I Scream, We all Scream for Ice Cream!', '1.50', '1', '2018-01-05 12:00:00');
INSERT INTO `epam_cafe`.`dish` (`type_id`, `name`, `description`, `dish_price`, `in_menu`, `create_date`) VALUES ('4', 'Pound Cake', 'Our pound cake is made with plenty of eggs, milk and butter.  Baked to', '2.50', '1', '2018-01-05 12:00:00');
INSERT INTO `epam_cafe`.`dish` (`type_id`, `name`, `dish_price`, `in_menu`, `create_date`) VALUES ('5', 'Espresso', '2.00', '1', '2018-01-05 12:00:00');
INSERT INTO `epam_cafe`.`dish` (`type_id`, `name`, `dish_price`, `in_menu`, `create_date`) VALUES ('5', 'Americano', '2.00', '1', '2018-01-05 12:00:00');
INSERT INTO `epam_cafe`.`dish` (`type_id`, `name`, `dish_price`, `in_menu`, `create_date`) VALUES ('5', 'Cappuciono', '3.00', '1', '2018-01-05 12:00:00');
INSERT INTO `epam_cafe`.`dish` (`type_id`, `name`, `dish_price`, `in_menu`, `create_date`) VALUES ('5', 'Latte', '3.00', '1', '2018-01-05 12:00:00');
INSERT INTO `epam_cafe`.`dish` (`type_id`, `name`, `dish_price`, `in_menu`, `create_date`) VALUES ('5', 'Black Tea', '2.00', '1', '2018-01-05 12:00:00');
INSERT INTO `epam_cafe`.`dish` (`type_id`, `name`, `dish_price`, `in_menu`, `create_date`) VALUES ('5', 'Green Tea', '2.00', '1', '2018-01-05 12:00:00');
INSERT INTO `epam_cafe`.`dish` (`type_id`, `name`, `dish_price`, `in_menu`, `create_date`) VALUES ('6', 'Fanta', '1.50', '1', '2018-01-05 12:00:00');
INSERT INTO `epam_cafe`.`dish` (`type_id`, `name`, `dish_price`, `in_menu`, `create_date`) VALUES ('6', 'Coca Cola', '1.50', '1', '2018-01-05 12:00:00');
INSERT INTO `epam_cafe`.`dish` (`type_id`, `name`, `dish_price`, `in_menu`, `create_date`) VALUES ('6', 'Sprite', '1.50', '1', '2018-01-05 12:00:00');
INSERT INTO `epam_cafe`.`dish` (`type_id`, `name`, `dish_price`, `in_menu`, `create_date`) VALUES ('6', 'Bonaqua', '1.50', '1', '2018-01-05 12:00:00');


-- -----------------------------------------------------
-- Table `epam_cafe`.`order`
-- -----------------------------------------------------


-- -----------------------------------------------------
-- Table `epam_cafe`.`order_has_dish`
-- -----------------------------------------------------






