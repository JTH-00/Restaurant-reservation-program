import { useState } from "react";
import styles from "./addMenu.module.scss";
import add from "../../assets/add.png";
import minus from "../../assets/minus.png";

const AddMenu = ({ handleCategoriesChange }) => {
  const [categories, setCategories] = useState([]);

  const handleAddCategory = () => {
    const updatedCategories = [
      ...categories,
      { name: "", menus: [{ name: "", price: "", file: null }] },
    ];

    setCategories(updatedCategories);
    handleCategoriesChange(updatedCategories);
  };

  const handleAddDetailMenu = (categoryIndex) => {
    const updatedCategories = [...categories];
    updatedCategories[categoryIndex].menus.push({
      name: "",
      price: "",
      file: null,
    });
    setCategories(updatedCategories);
  };

  const handleInputChange = (categoryIndex, menuIndex, field, value) => {
    const updatedCategories = [...categories];
    if (menuIndex === undefined) {
      updatedCategories[categoryIndex][field] = value;
    } else {
      updatedCategories[categoryIndex].menus[menuIndex][field] = value;
    }
    setCategories(updatedCategories);
  };

  const handleDeleteCategory = (categoryIndex) => {
    const updatedCategories = [...categories];
    updatedCategories.splice(categoryIndex, 1);
    setCategories(updatedCategories);
  };

  const handleDeleteDetailMenu = (categoryIndex, menuIndex) => {
    const updatedCategories = [...categories];
    updatedCategories[categoryIndex].menus.splice(menuIndex, 1);
    setCategories(updatedCategories);
  };
  console.log(categories);

  return (
    <div className={styles.form}>
      <div className={styles.category_header}>
        <p>
          카테고리
          <img src={add} type="button" onClick={handleAddCategory}></img>
        </p>
        <span>*사진은 메뉴당 한개입니다.</span>
      </div>

      {categories.map((category, categoryIndex) => (
        <div key={categoryIndex} className={styles.category_warpper}>
          <div className={styles.addmenu_wrapper}>
            {categoryIndex !== 0 && (
              <img
                src={minus}
                type="button"
                onClick={() => handleDeleteCategory(categoryIndex)}
              ></img>
            )}
            <input
              type="text"
              placeholder="카테고리 (ex : 메인메뉴)"
              value={category.name}
              onChange={(e) =>
                handleInputChange(
                  categoryIndex,
                  undefined,
                  "name",
                  e.target.value
                )
              }
            />
            <p>
              메뉴추가
              <img
                src={add}
                type="button"
                onClick={() => handleAddDetailMenu(categoryIndex)}
              ></img>
            </p>
          </div>

          {category.menus.map((menu, menuIndex) => (
            <div key={menuIndex} className={styles.menu_wrapper}>
              <img
                src={minus}
                type="button"
                onClick={() => handleDeleteDetailMenu(categoryIndex, menuIndex)}
              ></img>
              <input
                placeholder="상세메뉴"
                type="text"
                value={menu.name}
                onChange={(e) =>
                  handleInputChange(
                    categoryIndex,
                    menuIndex,
                    "name",
                    e.target.value
                  )
                }
              />
              <input
                placeholder="가격"
                type="text"
                value={menu.price}
                onChange={(e) =>
                  handleInputChange(
                    categoryIndex,
                    menuIndex,
                    "price",
                    e.target.value
                  )
                }
              />
              <input
                type="file"
                onChange={(e) =>
                  handleInputChange(
                    categoryIndex,
                    menuIndex,
                    "file",
                    e.target.files[0]
                  )
                }
              />
            </div>
          ))}
        </div>
      ))}
    </div>
  );
};

export default AddMenu;
