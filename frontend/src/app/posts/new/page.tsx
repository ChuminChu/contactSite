'use client';

import { useRouter } from 'next/navigation';
import { useState } from 'react';

export default function NewPostPage() {
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const router = useRouter();

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    console.log({ title, content });

    // TODO: 게시글 작성 API 호출

    router.push('/posts');
  };

  return (
      <div className="container max-w-2xl mx-auto p-4">
        <h1 className="text-2xl font-bold mb-4">새 게시글 작성</h1>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label htmlFor="title" className="block mb-2">
              제목
            </label>
            <input
                type="text"
                id="title"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                className="w-full p-2 border rounded"
                required
            />
          </div>
          <div>
            <label htmlFor="content" className="block mb-2">
              내용
            </label>
            <textarea
                id="content"
                value={content}
                onChange={(e) => setContent(e.target.value)}
                className="w-full p-2 border rounded h-40"
                required
            />
          </div>
          <button
              type="submit"
              className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
          >
            작성하기
          </button>
        </form>
      </div>
  );
}
