import { useState } from "react";

const useDateInfo = () => {
  const [startDateInfo, setStartDateInfo] = useState({});
  const [endDateInfo, setEndDateInfo] = useState({});

  const setStartDate = (date) => {
    const { year, month, day } = getDateInfo(date);
    setStartDateInfo({ year, month, day });
  };

  const setEndDate = (date) => {
    const { year, month, day } = getDateInfo(date);
    setEndDateInfo({ year, month, day });
  };

  const getDateInfo = (date) => {
    return {
      year: date.getFullYear(),
      month: date.getMonth() + 1,
      day: date.getDate(),
    };
  };

  return { startDateInfo, setStartDate, endDateInfo, setEndDate, getDateInfo };
};

export default useDateInfo;
