import React, { useState } from 'react';
import data from './data.json';

const itemsPerPage = 10; // Number of records to display per page

const Meja = React.memo(() => {
  const [currentPage, setCurrentPage] = useState(1);
  const [searchQuery, setSearchQuery] = useState('');
  const [filters, setFilters] = useState({
    gelaran: '',
    tahun: '',
    peringkat: '',
  });

  const handleNextPage = () => {
    setCurrentPage((prevPage) => prevPage + 1);
  };

  const handlePrevPage = () => {
    setCurrentPage((prevPage) => prevPage - 1);
  };

  const handleSearch = (event) => {
    setSearchQuery(event.target.value);
    setCurrentPage(1);
  };

  const handleFilterChange = (filterType, value) => {
    setFilters((prevFilters) => ({
      ...prevFilters,
      [filterType]: value,
    }));
    setCurrentPage(1);
  };

  const handleResetFilter = () => {
    setSearchQuery('');
    setFilters({
      gelaran: '',
      tahun: '',
      peringkat: '',
    });
    setCurrentPage(1);
  };

  const filteredData = data.filter((item) => {
    const isGelaranMatched = filters.gelaran
      ? item.gelaran.toLowerCase().includes(filters.gelaran.toLowerCase())
      : true;

    const isTahunMatched = filters.tahun ? item.tahun == filters.tahun : true;

    const isPeringkatMatched = filters.peringkat
      ? item.peringkat.toLowerCase().includes(filters.peringkat.toLowerCase())
      : true;

    const isSearchMatched = item.nama.toLowerCase().includes(searchQuery.toLowerCase());

    return isGelaranMatched && isTahunMatched && isPeringkatMatched && isSearchMatched;
  });

  const totalResults = filteredData.length;
  const totalPages = Math.ceil(totalResults / itemsPerPage);

  const startIndex = (currentPage - 1) * itemsPerPage;
  const endIndex = startIndex + itemsPerPage;
  const currentPageData = filteredData.slice(startIndex, endIndex);

  return (
    <div className='mx-10'>
      <div className='flex gap-2 justify-center h-10'>
        <input
          type='text'
          value={searchQuery}
          onChange={handleSearch}
          placeholder='Search by name...'
          className='block w-full border border-gray-300 rounded-md p-2'
        />
        <select
          name='gelaran'
          value={filters.gelaran}
          onChange={(e) => handleFilterChange('gelaran', e.target.value)}
          className='block w-full border border-gray-300 rounded-md p-2'
        >
          <option value=''>Filter Gelaran</option>
          {Array.from(new Set(data.map((item) => item.gelaran))).map((option) => (
            <option key={option} value={option}>
              {option}
            </option>
          ))}
        </select>
        <select
          name='tahun'
          value={filters.tahun}
          onChange={(e) => handleFilterChange('tahun', e.target.value)}
          className='block w-full border border-gray-300 rounded-md p-2'
        >
          <option value=''>Filter Tahun</option>
          {Array.from(new Set(data.map((item) => item.tahun))).map((option) => (
            <option key={option} value={option}>
              {option}
            </option>
          ))}
        </select>
        <select
          name='peringkat'
          value={filters.peringkat}
          onChange={(e) => handleFilterChange('peringkat', e.target.value)}
          className='block w-full border border-gray-300 rounded-md p-2'
        >
          <option value=''>Filter Peringkat</option>
          {Array.from(new Set(data.map((item) => item.peringkat))).map((option) => (
            <option key={option} value={option}>
              {option}
            </option>
          ))}
        </select>
        <button className='bg-yellow-500 text-white rounded-md px-4 py-2' onClick={handleResetFilter}>
          {"↻"}
        </button>
        <button
          onClick={handlePrevPage}
          disabled={currentPage === 1}
          className='bg-yellow-500 text-white rounded-md disabled:bg-gray-400 px-4 py-2'
        >
          {"<"}
        </button>
        <button
          onClick={handleNextPage}
          disabled={endIndex >= filteredData.length}
          className='bg-yellow-500 text-white rounded-md disabled:bg-gray-400 px-4 py-2'
        >
          {">"}
        </button>
      </div>
      <div className='my-4'>
        <span className='text-sm'>
          {totalResults} {totalResults === 1 ? 'result' : 'results'} out of {data.length}
        </span>
        <span className='text-sm float-right'>
          Page {currentPage} of {totalPages}
        </span>
      </div>

      <div>
      <table className='w-full text-sm text-left p-4 rounded-lg overflow-hidden shadow-xl'>
  <thead className='text-xs uppercase bg-yellow-600'>
    <tr>
      <th className='p-2'>Gelaran</th>
      <th className='p-2'>Nama</th>
      <th className='p-2'>Anugerah</th>
      <th className='p-2'>Tahun</th>
      <th className='p-2'>Peringkat</th>
    </tr>
  </thead>
  <tbody className='max-h-72 overflow-y-auto bg-yellow-500 '>
    {currentPageData.map((item) => (
      <tr key={item.no} className='text-sm uppercase hover:bg-yellow-400'>
        <td className='font-bold p-2'>{item.gelaran}</td>
        <td className='p-2'>{item.nama}</td>
        <td className='p-2'>{item.anugerah}</td>
        <td className='p-2'>{item.tahun}</td>
        <td className='p-2'>{item.peringkat}</td>
      </tr>
    ))}
  </tbody>
</table>

      
        
      </div>

    </div>
  );
});

export default Meja;
