"use client";

import { useEffect, useState } from "react";
import DevItem from "@/components/DevItem";
import "./developer.page.scss";
import { getAuthToken } from "@/app/login/actions";

interface Programmer {
  id: string;
  name: string;
  age: number;
  fieldName: string[];
  isLiked: boolean;
  likeCount: number;
}

interface PageData {
  totalPages: number;
  totalCount: number;
  currentPage: number;
  pageSize: number;
  programmerReadResponses: Programmer[];
}

export default function DeveloperListPage() {
  const [pageData, setPageData] = useState<PageData>({
    totalPages: 0,
    totalCount: 0,
    currentPage: 0,
    pageSize: 0,
    programmerReadResponses: [],
  });
  const [fields, setFields] = useState<string[]>([]);
  const [personalHistory, setPersonalHistory] = useState(0);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [currentPage, setCurrentPage] = useState(1);

  // 페이지네이션에 표시할 최대 페이지 버튼 수
  const MAX_PAGE_BUTTONS = 10;

  const handleCheckboxChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { value, checked } = e.target;
    if (checked) {
      setFields([...fields, value]);
    } else {
      setFields(fields.filter((field) => field !== value));
    }
  };

  const handleRadioChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPersonalHistory(Number(e.target.value));
  };

  const fetchDevs = async (page = 1) => {
    setIsLoading(true);
    setError(null);
    try {
      const token = await getAuthToken();
      const res = await fetch(
        `http://localhost:8080/programmers?field=${fields.join(",")}&personalHistory=${personalHistory}&page=${page}`,
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          cache: "no-store",
        },
      );

      if (!res.ok) throw new Error("Failed to fetch devs");
      const data = await res.json();
      console.log("Fetched data:", data);
      setPageData(data);
      setCurrentPage(page);
    } catch (err) {
      setError(err instanceof Error ? err.message : "An error occurred");
    } finally {
      setIsLoading(false);
    }
  };

  // Only fetch data once when the component mounts
  useEffect(() => {
    fetchDevs(1);
  }, []);

  const handlePageChange = (newPage: number) => {
    if (newPage < 1 || newPage > pageData.totalPages) return;
    fetchDevs(newPage);
  };

  const handleSearch = () => {
    // Reset to page 1 when performing a new search
    fetchDevs(1);
  };

  // 페이지네이션 렌더링 함수
  const renderPagination = () => {
    const totalPages = pageData.totalPages;
    if (totalPages <= 0) return null;

    // 표시할 페이지 범위 결정 (예: 현재 페이지 주변에 2개씩)
    const range = 2;
    let startPage = Math.max(1, currentPage - range);
    let endPage = Math.min(totalPages, currentPage + range);

    // 항상 5개 버튼 표시를 위한 조정
    if (endPage - startPage < 4) {
      if (startPage === 1) {
        endPage = Math.min(5, totalPages);
      } else if (endPage === totalPages) {
        startPage = Math.max(1, totalPages - 4);
      }
    }

    const pages = [];

    // 이전 페이지 버튼
    pages.push(
      <button
        key="prev"
        onClick={() => handlePageChange(currentPage - 1)}
        disabled={currentPage === 1}
        className="pagination-button prev-button"
        style={{
          padding: "8px 16px",
          border: "1px solid #e0e0e0",
          backgroundColor: "white",
          borderRadius: "4px",
          margin: "0 4px",
          cursor: currentPage === 1 ? "not-allowed" : "pointer",
          color: currentPage === 1 ? "#ccc" : "#333",
        }}
      >
        &lt;
      </button>,
    );

    // 첫 페이지가 표시되지 않을 경우 '1' 버튼 추가
    if (startPage > 1) {
      pages.push(
        <button
          key={1}
          onClick={() => handlePageChange(1)}
          className="pagination-button"
          style={{
            padding: "8px 16px",
            border: "1px solid #e0e0e0",
            backgroundColor: "white",
            borderRadius: "4px",
            margin: "0 4px",
            cursor: "pointer",
          }}
        >
          1
        </button>,
      );

      // 생략 표시 (...)
      if (startPage > 2) {
        pages.push(
          <span
            key="ellipsis1"
            style={{
              padding: "8px",
              margin: "0 4px",
            }}
          >
            ...
          </span>,
        );
      }
    }

    // 페이지 버튼
    for (let i = startPage; i <= endPage; i++) {
      pages.push(
        <button
          key={i}
          onClick={() => handlePageChange(i)}
          className={`pagination-button ${i === currentPage ? "active" : ""}`}
          style={{
            padding: "8px 16px",
            border: i === currentPage ? "none" : "1px solid #e0e0e0",
            backgroundColor: i === currentPage ? "#4CAF50" : "white",
            color: i === currentPage ? "white" : "#333",
            borderRadius: "4px",
            margin: "0 4px",
            cursor: "pointer",
            fontWeight: i === currentPage ? "bold" : "normal",
          }}
        >
          {i}
        </button>,
      );
    }

    // 마지막 페이지가 표시되지 않을 경우 생략 표시와 마지막 페이지 버튼 추가
    if (endPage < totalPages) {
      if (endPage < totalPages - 1) {
        pages.push(
          <span
            key="ellipsis2"
            style={{
              padding: "8px",
              margin: "0 4px",
            }}
          >
            ...
          </span>,
        );
      }

      pages.push(
        <button
          key={totalPages}
          onClick={() => handlePageChange(totalPages)}
          className="pagination-button"
          style={{
            padding: "8px 16px",
            border: "1px solid #e0e0e0",
            backgroundColor: "white",
            borderRadius: "4px",
            margin: "0 4px",
            cursor: "pointer",
          }}
        >
          {totalPages}
        </button>,
      );
    }

    // 다음 페이지 버튼
    pages.push(
      <button
        key="next"
        onClick={() => handlePageChange(currentPage + 1)}
        disabled={currentPage === totalPages}
        className="pagination-button next-button"
        style={{
          padding: "8px 16px",
          border: "1px solid #e0e0e0",
          backgroundColor: "white",
          borderRadius: "4px",
          margin: "0 4px",
          cursor: currentPage === totalPages ? "not-allowed" : "pointer",
          color: currentPage === totalPages ? "#ccc" : "#333",
        }}
      >
        &gt;
      </button>,
    );

    return pages;
  };

  // 페이지 버튼 스타일
  const getPageButtonStyle = (isActive: boolean) => {
    return {
      padding: "5px 12px",
      margin: "0 3px",
      backgroundColor: isActive ? "#007BFF" : "#f8f9fa",
      color: isActive ? "white" : "#212529",
      border: "1px solid #dee2e6",
      borderRadius: "5px",
      cursor: "pointer",
      fontSize: "14px",
      fontWeight: isActive ? "bold" : "normal",
    };
  };

  const experiences = [
    { value: 0, label: "신입" },
    { value: 3, label: "3년 이상" },
    { value: 5, label: "5년 이상" },
    { value: 10, label: "10년 이상" },
    { value: 15, label: "15년 이상" },
  ];

  return (
    <div className="container devlist">
      <div className="search">
        <div className={"select-options"}>
          <label className={"label"}>검색 옵션</label>
          <div className={"phcontainer"}>
            <label className={"label"}>경력</label>
            <div className={"personalHistory"}>
              {experiences.map((exp) => (
                <div className="checkboxContainer" key={exp.value}>
                  <input
                    type="radio"
                    value={exp.value}
                    id={`exp-${exp.value}`}
                    name="personalHistory"
                    onChange={handleRadioChange}
                    defaultChecked={exp.value === 0}
                  />
                  <label htmlFor={`exp-${exp.value}`} className="checkboxLabel">
                    {exp.label}
                  </label>
                </div>
              ))}
            </div>
          </div>
          <div className={"fieldContainer"}>
            <label className={"label"}>분야</label>
            <div className={"field"}>
              {[
                "Full_Stack",
                "Front_End",
                "Back_End",
                "DBManage",
                "ServerManage",
              ].map((field) => (
                <div className={"checkboxContainer"} key={field}>
                  <input
                    type="checkbox"
                    value={field}
                    id={field.toLowerCase()}
                    onChange={handleCheckboxChange}
                  />
                  <label
                    htmlFor={field.toLowerCase()}
                    className={"checkboxLabel"}
                  >
                    {field.replace("_", " ")}
                  </label>
                </div>
              ))}
            </div>
          </div>
        </div>
        <button className="button" onClick={handleSearch}>
          조회
        </button>
      </div>
      <div className="container devlist">
        <header>
          <h3>개발자 목록</h3>
        </header>
        <section>
          {isLoading ? (
            <p>Loading...</p>
          ) : error ? (
            <p>Error: {error}</p>
          ) : pageData.programmerReadResponses.length > 0 ? (
            <>
              {pageData.programmerReadResponses.map((programmer) => (
                <DevItem
                  key={programmer.id}
                  id={programmer.id}
                  name={programmer.name}
                  age={programmer.age}
                  field={programmer.fieldName}
                  isLiked={programmer.isLiked}
                  likeCount={programmer.likeCount}
                />
              ))}
            </>
          ) : (
            <p>No programmers found.</p>
          )}
        </section>
        {/* 페이지네이션 영역 */}
        <div style={{ textAlign: "center", marginTop: "20px" }}>
          {renderPagination()}
        </div>
      </div>
    </div>
  );
}
