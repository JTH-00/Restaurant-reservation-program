import cafeRestaurant from "../assets/cafeRestaurant.png";
import gogiRestaurant from "../assets/gogiRestaurant.png";
// import kareRestaurant from "../assets/kareRestaurant.png";
import donkkasRestaurant from "../assets/donkkasRestaurant.png";

export const restaurantArray = [
  ["카페", 3, cafeRestaurant, 3, 2, 1],
  ["삼겹살", 20, gogiRestaurant, 1, 4, 2],
  ["카레", 10, cafeRestaurant, 4, 1, 3],
  ["일식 돈까스", 23, donkkasRestaurant, 5, 0, 4],
];

export const restaurantImg = {
  cafe: cafeRestaurant,
  gogi: gogiRestaurant,
  kare: cafeRestaurant,
  donkka: donkkasRestaurant,
};

export const restaurantDetail = {
  id: 1,
  restaurantName: "삼겹살집",
  address: "부천 심곡동",
  detailAddress: "경기도 부천시 원미구 심곡동 부일로 442",
  callNumber: "010-xxxx-xxxx",
  restaurantOpen: {
    mon: "10:00 ~ 22:00",
    tue: "10:00 ~ 22:00",
    wed: "10:00 ~ 22:00",
    thu: "10:00 ~ 22:00",
    fri: "10:00 ~ 22:00",
    sat: "10:00 ~ 22:00",
    sun: "10:00 ~ 22:00",
  },
  introduce:
    "부천역 북부 인근에 위치한 고깃집 입니다 잊지못할 고기를 드셔보세요.",
  restaurantInfo: {
    basicInfo: ["24시간 영업하는 고깃집", "매장 내 금연"],
    rule: [
      "예약일 기준 2일전 100% 환불",
      "예약일 기준 1일전 50% 환불",
      "예약일 당일 환불 불가",
    ],
    origin: ["삼겹살:미국산", "목살:미국산", "대패삼겹살:미국산", "쌀:국내산"],
  },
  restaurantClose: "명절을 제외한 연중무휴",
  starScore: "5.0",
  reviewCounter: 15,
};

export const menuCategories = [
  "메인메뉴",
  "사이드메뉴",
  "기타메뉴",
  "주류 및 음료",
];

export const realMenu = {
  id: 1,
  mainMenu: [
    ["삼겹살", gogiRestaurant],
    ["목살", gogiRestaurant],
    ["대패삼겹살", gogiRestaurant],
  ],
  sideMenu: [
    ["계란찜", gogiRestaurant],
    ["된장찌개", gogiRestaurant],
    ["냉면", gogiRestaurant],
  ],
  etcMenu: [["공기밥", gogiRestaurant]],
  drink: [
    ["소주", gogiRestaurant],
    ["맥주", gogiRestaurant],
    ["콜라", gogiRestaurant],
    ["사이다", gogiRestaurant],
  ],
};

export const restaurantReview = {
  id: 1,
  date: "Fri Mar 01 2024 18:02:58 GMT+0900 ",
  username: "함승완",
  review:
    "인생 최고의 삼겹살 이였습니다.다음에도 이용할 예정입니다.부천 심곡에서 고기드실분은 여기로 오세요. 제가 아는 삼겹살집 중에서 가성비 좋고 최고였습니다. 다들 즐거운 식사 하시길 바랍니다",
};
