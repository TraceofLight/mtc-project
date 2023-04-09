import { useState, useRef } from "react";
import InputBase from "@mui/material/InputBase";
import IconButton from "@mui/material/IconButton";
import SearchIcon from "@mui/icons-material/Search";
import SearchList from "./SearchList";
import TagList from "./TagList";
import Chip from "@mui/material/Chip";
import Stack from "@mui/material/Stack";
import ClearIcon from "@mui/icons-material/Clear";

const SearchBar = () => {
  const inputRef = useRef(null);
  const [keyword, setKeyword] = useState("");
  const [result, setResult] = useState(false);
  const [searchMode, setSearchMode] = useState("none");

  const typeKeyword = (event) => {
    if (event.target.value.length === 0) {
      setSearchMode("none");
    } else if (event.target.value[0] === "#") {
      setSearchMode("tag");
    } else {
      setSearchMode("keyword");
    }
    setKeyword(event.target.value);
  };

  const TagSearch = (tag) => {
    setResult(true);
    setKeyword(tag);
    setSearchMode("tagSearch");
    console.log("asdfasdf");
    if (inputRef.current) {
      inputRef.current.value = "";
    }
  };

  const handleDelete = () => {
    setResult(false);
    setKeyword("");
    setSearchMode("none");
  };

  const typeSearch = () => {
    switch (searchMode) {
      case "keyword":
        return <SearchList keyword={keyword} search={"keyword"} />;
      case "tag":
        return <TagList keyword={keyword.slice(1)} TagSearch={TagSearch} />;
      case "tagSearch":
        return <SearchList keyword={keyword} search={"tag"} />;
      default:
        return null;
    }
  };

  return (
    <>
      <div
        style={{
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
          marginTop: "7%",
        }}
      >
        {result ? (
          // <InputBase sx={{ width: 0.7, borderBottom: 1 }}> <Chip label={"#"+keyword} color="primary" onDelete={handleDelete}/></InputBase>
          <InputBase
            sx={{ width: 0.7, borderBottom: 1 }}
            startAdornment={
              <Stack direction="row">
                <b>
                  <Chip
                    label={<span style={{ fontSize: '1.3em' }}>{"#"+keyword}</span>}
                    onDelete={handleDelete}
                    sx={{ bgcolor: "#6DCEF5" , zIndex:4}}
                    deleteIcon={<ClearIcon sx={{ color: "error", zIndex:3 }} />
                    }
                  />
                </b>
              </Stack>
            }
            endAdornment={
              <IconButton type="button" sx={{ p: "0.6em" }}>
                <SearchIcon />
              </IconButton>
            }
            inputRef={inputRef}
          />
        ) : (
          <InputBase
            sx={{ width: 0.7, borderBottom: 1 }}
            placeholder="#해시태그, 제목 검색"
            onChange={typeKeyword}
            inputRef={inputRef}
            endAdornment={
              <IconButton type="button" sx={{ p: "10px" }}>
                <SearchIcon />
              </IconButton>
            }
          />
        )}
      </div>
      <div>{typeSearch()}</div>
    </>
  );
};
export default SearchBar;
